package me.swipez.customenchants.runnables;

import me.swipez.customenchants.RayCast.Raycast;
import me.swipez.customenchants.stored.HomedLinkedArrows;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class ArrowHomingRunnable extends BukkitRunnable {
    @Override
    public void run() {

        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                UUID uuid = entity.getUniqueId();
                if (entity instanceof FallingBlock){
                    if (HomedLinkedArrows.battedBlocks.contains(entity)){
                        for (Entity entity1 : entity.getNearbyEntities(0.5,0.5,0.5)){
                            if (entity1 instanceof LivingEntity){
                                LivingEntity livingEntity = (LivingEntity) entity1;
                                livingEntity.damage(20);
                            }
                        }
                    }
                }
                if (HomedLinkedArrows.storedHomingArrows.containsKey(uuid)) {
                    Arrow arrow = (Arrow) entity;
                    if (!arrow.isInBlock()) {
                        Player player = Bukkit.getPlayer(HomedLinkedArrows.getArrowShooter(arrow));
                        if (player != null) {
                            arrow.setRotation(player.getLocation().getYaw()*-1, player.getLocation().getPitch()*-1);
                            LivingEntity livingEntity = getMobPlayerIsLookingAt(player);
                            if (livingEntity != null){
                                if (!HomedLinkedArrows.TargetedEntity.containsValue(livingEntity) || HomedLinkedArrows.TargetedEntity.get(player.getUniqueId()) != livingEntity) {
                                    arrow.setVelocity(livingEntity.getLocation().toVector().subtract(arrow.getLocation().toVector()).normalize().multiply(2));
                                    livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 6, 1, false, false));
                                    HomedLinkedArrows.TargetedEntity.put(uuid, livingEntity);
                                }
                            }
                            else {
                                if (HomedLinkedArrows.TargetedEntity.get(uuid) != null) {
                                    LivingEntity livingEntity1 = HomedLinkedArrows.TargetedEntity.get(uuid);
                                    arrow.setVelocity(livingEntity1.getLocation().toVector().subtract(arrow.getLocation().toVector()).normalize().multiply(2));
                                    livingEntity1.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 6, 1, false, false));
                                }
                                else {
                                    arrow.setVelocity(player.getLocation().getDirection().normalize().multiply(2));
                                    arrow.setVelocity(arrow.getVelocity().multiply(0.10));
                                }
                            }
                        }
                    }
                    else {
                        HomedLinkedArrows.storedHomingArrows.remove(uuid);
                        HomedLinkedArrows.TargetedEntity.remove(uuid);
                    }
                }
            }
        }
    }

    LivingEntity getMobPlayerIsLookingAt(Player player) {
        Raycast raycast = new Raycast(player.getEyeLocation(), 100);
        if (raycast.compute(Raycast.RaycastType.ENTITY)){
            Entity entity = raycast.getHurtEntity();
            if (entity instanceof LivingEntity && !(entity instanceof Arrow) && !(entity instanceof Player)){
                return (LivingEntity) entity;
            }
        }
        return null;
    }
}

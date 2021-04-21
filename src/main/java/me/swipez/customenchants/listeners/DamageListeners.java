package me.swipez.customenchants.listeners;

import me.swipez.customenchants.enchantments.SpecialEnchantments;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class DamageListeners implements Listener {
    @EventHandler
    public void onPlayerDamageEntity(EntityDamageByEntityEvent event){
        if (event.getDamager() instanceof Player){
            Player player = (Player) event.getDamager();
            ItemStack heldItem = player.getInventory().getItemInMainHand();
            if (heldItem.getItemMeta() == null){
                return;
            }
            List<String> strings = heldItem.getItemMeta().getLore();
            if (strings == null){
                return;
            }
            if (!(event.getEntity() instanceof LivingEntity)){
                return;
            }
            if (SpecialEnchantments.isAppliedEnchantment(strings, "electricity")) {
                event.getEntity().getWorld().strikeLightningEffect(event.getEntity().getLocation());
                LivingEntity livingEntity = (LivingEntity) event.getEntity();
                livingEntity.damage(5);
                livingEntity.getWorld().getBlockAt(livingEntity.getLocation()).setType(Material.FIRE);
                if (livingEntity instanceof Creeper){
                    Creeper creeper = (Creeper) livingEntity;
                    creeper.setPowered(true);
                }
            }
        }
    }
    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event){
        if (event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            if (hasVampireArmor(player)) {
                Entity entity = event.getDamager();
                if (entity instanceof LivingEntity){
                    event.setCancelled(true);
                    LivingEntity livingEntity = (LivingEntity) entity;
                    livingEntity.damage(event.getDamage());
                    player.setHealth(20);
                }
            }
            if (hasRepelArmor(player)){
                Entity entity = event.getDamager();
                if (entity instanceof LivingEntity){
                    LivingEntity livingEntity = (LivingEntity) entity;
                    livingEntity.setVelocity(player.getLocation().getDirection().multiply(2));
                }
            }
        }
    }
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){
        if (event.getEntity().getKiller() != null){
            Player player = event.getEntity().getKiller();
            LivingEntity livingEntity = event.getEntity();
            ItemStack heldItem = player.getInventory().getItemInMainHand();
            ItemMeta meta = heldItem.getItemMeta();
            if (meta == null){
                return;
            }
            List<String> strings = meta.getLore();
            if (strings == null){
                return;
            }
            if (SpecialEnchantments.isAppliedEnchantment(strings, "extinction")){
                for (Entity entity : player.getNearbyEntities(100,100,100)){
                    if (entity.getType().equals(livingEntity.getType())){
                        LivingEntity livingEntity1 = (LivingEntity) entity;
                        livingEntity1.setHealth(0);
                    }
                }
            }
        }
    }

    public boolean hasVampireArmor(Player player) {
        boolean bool = false;
        try {
            Integer[] armorSlots = new Integer[] {
                    36,37,38,39,40
            };
            for (Integer integer : armorSlots){
                ItemStack itemStack = player.getInventory().getItem(integer);
                if (itemStack != null){
                    ItemMeta meta = itemStack.getItemMeta();
                    if (meta != null){
                        if (meta.getLore() != null){
                            if (SpecialEnchantments.isAppliedEnchantment(meta.getLore(), "vampire")){
                                bool = true;
                            }
                        }
                    }
                }
            }
        }
        catch (NullPointerException exception) {
            // bruh, no armor? smh
        }
        return bool;
    }

    public boolean hasRepelArmor(Player player) {
        boolean bool = false;
        try {
            Integer[] armorSlots = new Integer[] {
                    36,37,38,39,40
            };
            for (Integer integer : armorSlots){
                ItemStack itemStack = player.getInventory().getItem(integer);
                if (itemStack != null){
                    ItemMeta meta = itemStack.getItemMeta();
                    if (meta != null){
                        if (meta.getLore() != null){
                            if (SpecialEnchantments.isAppliedEnchantment(meta.getLore(), "repel")){
                                bool = true;
                            }
                        }
                    }
                }
            }
        }
        catch (NullPointerException exception) {
            // bruh, no armor? smh
        }
        return bool;
    }
}

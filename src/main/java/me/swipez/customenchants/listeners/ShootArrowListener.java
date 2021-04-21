package me.swipez.customenchants.listeners;

import me.swipez.customenchants.enchantments.SpecialEnchantments;
import me.swipez.customenchants.stored.HomedLinkedArrows;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ShootArrowListener implements Listener {
    @EventHandler
    public void onPlayerShootArrow(EntityShootBowEvent event){
        if (event.getEntity() instanceof Player){
            ItemMeta meta = event.getBow().getItemMeta();
            if (meta == null){
                return;
            }
            List<String> strings = meta.getLore();
            if (strings == null){
                return;
            }
            if (SpecialEnchantments.isAppliedEnchantment(strings, "aimbot")){
                if (event.getProjectile() instanceof Arrow){
                    Arrow arrow = (Arrow) event.getProjectile();
                    HomedLinkedArrows.storedHomingArrows.put(arrow.getUniqueId(), event.getEntity().getUniqueId());
                }
            }
        }
    }
}

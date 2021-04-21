package me.swipez.customenchants.listeners;

import me.swipez.customenchants.enchantments.EnchantsPicker;
import me.swipez.customenchants.enchantments.SpecialEnchantments;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantListener implements Listener {
    @EventHandler
    public void onPlayerEnchant(EnchantItemEvent event){
        EnchantsPicker enchantsPicker = new EnchantsPicker();
        ItemStack itemStack = event.getItem();
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null){
            return;
        }
        if (event.getExpLevelCost() == 2){
            if (isPickaxe(itemStack)){
                SpecialEnchantments.applyLoreSafely(enchantsPicker.pickPickaxeEnchant(2), itemMeta);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            if (isSword(itemStack)){
                SpecialEnchantments.applyLoreSafely(enchantsPicker.pickSwordEnchant(2), itemMeta);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            if (isArmor(itemStack)){
                SpecialEnchantments.applyLoreSafely(enchantsPicker.pickArmorEnchant(2), itemMeta);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            itemStack.setItemMeta(itemMeta);
        }
        if (event.getExpLevelCost() == 10){
            if (isPickaxe(itemStack)){
                SpecialEnchantments.applyLoreSafely(enchantsPicker.pickPickaxeEnchant(10), itemMeta);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            if (isAxe(itemStack)){
                SpecialEnchantments.applyLoreSafely(enchantsPicker.pickAxeEnchant(10), itemMeta);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            itemStack.setItemMeta(itemMeta);
        }
        if (event.getExpLevelCost() == 20){
            if (isBow(itemStack)){
                SpecialEnchantments.applyLoreSafely(enchantsPicker.pickBowEnchant(20), itemMeta);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            if (isShovel(itemStack)){
                SpecialEnchantments.applyLoreSafely(enchantsPicker.pickShovelEnchant(20), itemMeta);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            itemStack.setItemMeta(itemMeta);
        }
        if (event.getExpLevelCost() == 30){
            if (isSword(itemStack)){
                SpecialEnchantments.applyLoreSafely(enchantsPicker.pickSwordEnchant(30), itemMeta);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            if (isArmor(itemStack)){
                SpecialEnchantments.applyLoreSafely(enchantsPicker.pickArmorEnchant(30), itemMeta);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            itemStack.setItemMeta(itemMeta);
        }
    }

    public boolean isPickaxe(ItemStack itemStack){
        return itemStack.getType().toString().contains("_PICKAXE");
    }

    public boolean isSword(ItemStack itemStack){
        return itemStack.getType().toString().contains("_SWORD");
    }

    public boolean isArmor(ItemStack itemStack){
        return itemStack.getType().toString().contains("_HELMET") || itemStack.getType().toString().contains("CHESTPLATE") || itemStack.getType().toString().contains("LEGGINGS") || itemStack.getType().toString().contains("BOOTS");
    }

    public boolean isShovel(ItemStack itemStack){
        return itemStack.getType().toString().contains("_SHOVEL");
    }

    public boolean isAxe(ItemStack itemStack){
        return itemStack.getType().toString().contains("_AXE");
    }

    public boolean isBow(ItemStack itemStack){
        return itemStack.getType().toString().contains("BOW");
    }
}

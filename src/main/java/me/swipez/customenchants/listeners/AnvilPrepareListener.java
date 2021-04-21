package me.swipez.customenchants.listeners;

import me.swipez.customenchants.CustomEnchants;
import me.swipez.customenchants.enchantments.SpecialEnchantments;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentOffer;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareSmithingEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class AnvilPrepareListener implements Listener {

    CustomEnchants plugin;

    public AnvilPrepareListener(CustomEnchants plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAnvilPrepare(PrepareAnvilEvent event) {
        if (event.getInventory().getItem(0) != null) {
            if (event.getInventory().getItem(1) != null) {
                if (event.getInventory().getItem(0).getType() == event.getInventory().getItem(1).getType()) {
                    ItemStack firstItem = event.getInventory().getItem(0);
                    ItemStack secondItem = event.getInventory().getItem(1);
                    List<String> firstList = firstItem.getItemMeta().getLore();
                    List<String> secondList = secondItem.getItemMeta().getLore();
                    if (firstList != null && secondList != null) {
                        for (String string : secondList) {
                            if (!firstList.contains(string)) {
                                firstList.add(string);
                            }
                        }
                        ItemStack resultStack = event.getResult().clone();
                        ItemMeta meta = resultStack.getItemMeta();
                        meta.setLore(firstList);
                        if (!event.getInventory().getRenameText().isEmpty()) {
                            meta.setDisplayName(event.getInventory().getRenameText());
                        } else {
                            meta.setDisplayName(firstItem.getItemMeta().getDisplayName());
                        }
                        resultStack.setItemMeta(meta);
                        event.setResult(resultStack);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onSmithingTablePrepare(PrepareSmithingEvent event) {
        if (event.getInventory().getItem(0) != null) {
            if (event.getInventory().getItem(1) != null) {
                if (event.getInventory().getItem(0).getType() == event.getInventory().getItem(1).getType()) {
                    ItemStack firstItem = event.getInventory().getItem(0);
                    List<String> firstList = firstItem.getItemMeta().getLore();
                    ItemStack resultStack = event.getResult().clone();
                    ItemMeta meta = resultStack.getItemMeta();
                    meta.setLore(firstList);
                    resultStack.setItemMeta(meta);
                    event.setResult(resultStack);
                }
            }
        }
    }
    @EventHandler
    public void onEnchantPrepare(PrepareItemEnchantEvent event){
        Enchantment custom_enchant = SpecialEnchantments.getCustomEnchantPlacehold(plugin);
        EnchantmentOffer[] enchantmentOffers = event.getOffers();
        ItemStack itemStack = event.getItem();
        if (enchantmentOffers[2] == null){
            return;
        }
        if (event.getEnchantmentBonus() >= 15){
            if (isSword(itemStack) || isArmor(itemStack)){
                enchantmentOffers[2].setEnchantment(custom_enchant);
                enchantmentOffers[2].setEnchantmentLevel(1);
                enchantmentOffers[2].setCost(30);
            }
        }
        else if (event.getEnchantmentBonus() >= 10){
            if (isBow(itemStack) || isShovel(itemStack)){
                enchantmentOffers[2].setEnchantment(custom_enchant);
                enchantmentOffers[2].setEnchantmentLevel(1);
                enchantmentOffers[2].setCost(20);
            }
        }
        else if (event.getEnchantmentBonus() >= 5){
            if (isPickaxe(itemStack) || isAxe(itemStack)){
                enchantmentOffers[2].setEnchantment(custom_enchant);
                enchantmentOffers[2].setEnchantmentLevel(1);
                enchantmentOffers[2].setCost(10);
            }
        }
        else if (event.getEnchantmentBonus() >= 0){
            if (isPickaxe(itemStack) || isSword(itemStack) || isArmor(itemStack)){
                enchantmentOffers[2].setEnchantment(custom_enchant);
                enchantmentOffers[2].setEnchantmentLevel(1);
                enchantmentOffers[2].setCost(2);
            }
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

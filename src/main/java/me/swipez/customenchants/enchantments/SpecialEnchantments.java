package me.swipez.customenchants.enchantments;

import me.swipez.customenchants.CustomEnchants;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SpecialEnchantments {

    public static Enchantment getCustomEnchantPlacehold(CustomEnchants enchants){

        return new Enchantment(new NamespacedKey(enchants, "custom_enchant_placeholder")) {
            @Override
            public String getName() {
                return "Custom Enchantment";
            }

            @Override
            public int getMaxLevel() {
                return 1;
            }

            @Override
            public int getStartLevel() {
                return 0;
            }

            @Override
            public EnchantmentTarget getItemTarget() {
                return null;
            }

            @Override
            public boolean isTreasure() {
                return false;
            }

            @Override
            public boolean isCursed() {
                return false;
            }

            @Override
            public boolean conflictsWith(Enchantment other) {
                return false;
            }

            @Override
            public boolean canEnchantItem(ItemStack item) {
                return true;
            }
        };
    }

    public static boolean isAppliedEnchantment(List<String> lore, String enchantment){
        for (String string : lore){
            if (string.toLowerCase().contains(enchantment.toLowerCase())){
                return true;
            }
        }
        return false;
    }

    public static int getAppliedLevel(List<String> lore, String enchantment){
        for (String string : lore){
            if (string.toLowerCase().contains(enchantment)){
                String[] level = string.split(enchantment);
                return Integer.parseInt(level[1].replace(" ", ""));
            }
        }
        return 0;
    }

    public static void applyLoreSafely(String string, ItemMeta meta){
        List<String> lore = new ArrayList<>();
        if (meta.hasLore()){
            lore = meta.getLore();
        }
        lore.add(string);
        meta.setLore(lore);
    }
}

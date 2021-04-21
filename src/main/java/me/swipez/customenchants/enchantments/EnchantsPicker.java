package me.swipez.customenchants.enchantments;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnchantsPicker {
    Random random = new Random();

    private static List<String> pickaxeLevel2 = new ArrayList<>();

    static {
        pickaxeLevel2.add(ChatColor.GREEN+"Strip 200");
        pickaxeLevel2.add(ChatColor.AQUA+"Upgrade 147");
    }

    public String pickPickaxeEnchant(int minimum){
        if (minimum == 2){
            if (random.nextBoolean()){
                return pickaxeLevel2.get(0);
            }
            else {
                return pickaxeLevel2.get(1);
            }
        }
        if (minimum == 10){
            return ChatColor.AQUA+"Shower 108";
        }
        else {
            return null;
        }
    }

    public String pickSwordEnchant(int minimum){
        if (minimum == 2){
            return ChatColor.GOLD+"Electricity 122";
        }
        if (minimum == 30){
            return ChatColor.DARK_RED+"Extinction 127";
        }
        else {
            return null;
        }
    }

    public String pickArmorEnchant(int minimum){
        if (minimum == 2){
            return ChatColor.RED+"Vampire 189";
        }
        if (minimum == 30){
            return ChatColor.GREEN+"Repel 112";
        }
        else {
            return null;
        }
    }

    public String pickAxeEnchant(int minimum){
        if (minimum == 10){
            return ChatColor.DARK_PURPLE+"Timber 134";
        }
        else {
            return null;
        }
    }
    public String pickBowEnchant(int minimum){
        if (minimum == 20){
            return ChatColor.GOLD+"AimBot 175";
        }
        else {
            return null;
        }
    }
    public String pickShovelEnchant(int minimum){
        if (minimum == 20){
            return ChatColor.BLUE+"Batter 158";
        }
        else {
            return null;
        }
    }
}

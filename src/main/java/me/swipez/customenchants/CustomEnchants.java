package me.swipez.customenchants;

import me.swipez.customenchants.enchantments.SpecialEnchantments;
import me.swipez.customenchants.listeners.*;
import me.swipez.customenchants.runnables.ArrowHomingRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public final class CustomEnchants extends JavaPlugin {

    @Override
    public void onEnable() {
        registerBottleEnchant();
        getServer().getPluginManager().registerEvents(new ShootArrowListener(), this);
        BukkitTask HomingArrowRunnable = new ArrowHomingRunnable().runTaskTimer(this, 1, 1);
        getServer().getPluginManager().registerEvents(new AnvilPrepareListener(this), this);
        getServer().getPluginManager().registerEvents(new DamageListeners(), this);
        getServer().getPluginManager().registerEvents(new EnchantListener(), this);
        getServer().getPluginManager().registerEvents(new BlockMine(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void registerBottleEnchant(){
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this, "experience_bottle_custom_recipe"), new ItemStack(Material.EXPERIENCE_BOTTLE, 16))
                .shape(" R ", "RBR", " R ")
                .setIngredient('R', Material.REDSTONE)
                .setIngredient('B', Material.GLASS_BOTTLE);
        Bukkit.addRecipe(recipe);
    }
}

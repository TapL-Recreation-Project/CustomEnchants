package me.swipez.customenchants.listeners;

import me.swipez.customenchants.enchantments.SpecialEnchantments;
import me.swipez.customenchants.stored.HomedLinkedArrows;
import me.swipez.customenchants.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BlockMine implements Listener {
    @EventHandler
    public void onBlockMine(BlockBreakEvent event){
        Player player = event.getPlayer();
        ItemStack mainHand = event.getPlayer().getInventory().getItemInMainHand();
        ItemMeta meta = mainHand.getItemMeta();
        if (meta == null){
            return;
        }
        List<String> strings = meta.getLore();
        if (strings == null){
            return;
        }
        if (SpecialEnchantments.isAppliedEnchantment(strings, "strip")) {
            List<Block> blocksInRow = getBlocks(event, 100);
            for (Block block : blocksInRow){
                if (block.getType() != Material.BEDROCK){
                    block.breakNaturally(event.getPlayer().getInventory().getItemInMainHand());
                }
            }
        }
        if (SpecialEnchantments.isAppliedEnchantment(strings, "batter")) {
            Vector direction = event.getPlayer().getLocation().getDirection();
            if (direction.getY() < 0){
                direction.setY(direction.getY()*-1);
            }
            direction.setY(direction.getY()*2);
            if (direction.getY() > 0.6){
                direction.setY(0.6);
            }
            Material material = event.getBlock().getType();
            event.setDropItems(false);
            FallingBlock fallingBlock = event.getBlock().getWorld().spawnFallingBlock(event.getBlock().getLocation(), material.createBlockData());
            HomedLinkedArrows.battedBlocks.add(fallingBlock);
            fallingBlock.setVelocity(direction);
        }
        if (SpecialEnchantments.isAppliedEnchantment(strings, "shower")) {
            Material material = event.getBlock().getType();
            Random random = new Random();
            for (int i = 0; i < 20; i++){
                Location location = event.getBlock().getLocation();
                location.setY(location.getY()+30);
                int randX = random.nextInt(40);
                int randZ = random.nextInt(40);
                if (random.nextBoolean()){
                    randX *=-1;
                }
                if (random.nextBoolean()){
                    randZ *=-1;
                }
                location.setX(location.getX()+randX);
                location.setZ(location.getZ()+randZ);
                if (event.getBlock().getWorld().getBlockAt(location).getType() == Material.AIR){
                    event.getBlock().getWorld().spawnFallingBlock(location, material.createBlockData());
                }
            }
        }
        if (SpecialEnchantments.isAppliedEnchantment(strings, "timber")){
            Material brokenMaterial = event.getBlock().getType();
            if (brokenMaterial.toString().contains("LOG")){
                breakAdjacentWood(event.getBlock().getLocation(), brokenMaterial);
            }
        }
        if (SpecialEnchantments.isAppliedEnchantment(strings, "upgrade")){
            Material material = event.getBlock().getType();
            Block block = event.getBlock();
            if (material.equals(Material.DIAMOND_ORE)){
                block.setType(Material.DIAMOND_BLOCK);
                event.setCancelled(true);
            }
            if (material.equals(Material.IRON_ORE)){
                block.setType(Material.IRON_BLOCK);
                event.setCancelled(true);
            }
            if (material.equals(Material.REDSTONE_ORE)){
                block.setType(Material.REDSTONE_BLOCK);
                event.setCancelled(true);
            }
            if (material.equals(Material.GOLD_ORE)){
                block.setType(Material.GOLD_BLOCK);
                event.setCancelled(true);
            }
            if (material.equals(Material.LAPIS_ORE)){
                block.setType(Material.LAPIS_BLOCK);
                event.setCancelled(true);
            }
            if (material.equals(Material.NETHER_GOLD_ORE)){
                block.setType(Material.GOLD_BLOCK);
                event.setCancelled(true);
            }
            if (material.equals(Material.ANCIENT_DEBRIS)){
                block.setType(Material.NETHERITE_BLOCK);
                event.setCancelled(true);
            }
            if (material.equals(Material.NETHER_QUARTZ_ORE)){
                block.setType(Material.QUARTZ_BLOCK);
                event.setCancelled(true);
            }
            if (material.equals(Material.COAL_ORE)){
                block.setType(Material.COAL_BLOCK);
                event.setCancelled(true);
            }
        }
    }

    public void breakAdjacentWood(Location origin, Material woodMaterial) {
        BlockFace[] blockFaces = new BlockFace[] { BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST,
                BlockFace.UP, BlockFace.DOWN };
        for (BlockFace face : blockFaces) {
            Block relative = origin.getBlock().getRelative(face);
            origin.getBlock().breakNaturally();
            if (relative.getType() == woodMaterial) {
                breakAdjacentWood(relative.getLocation(), woodMaterial);
            }
        }
    }

    public List<Block> getBlocks(BlockBreakEvent e, int length) {
        List<Block> blockList = new ArrayList<>();

        Location blockLocation = e.getBlock().getLocation();

        //Primary
        int firstx = blockLocation.getBlockX();
        int firsty = blockLocation.getBlockY()-1;
        int firstz = blockLocation.getBlockZ();

        //Secondary
        int secondx = blockLocation.getBlockX();
        int secondy = blockLocation.getBlockY()+1;
        int secondz = blockLocation.getBlockZ();

        String playerDirection = Utils.getUpOrDown(e.getPlayer().getLocation().getPitch());

        if (playerDirection.equals("DOWN")){
            return blockList;
        }
        else if (playerDirection.equals("UP")){
            return blockList;
        }
        else if (e.getPlayer().getFacing().equals(BlockFace.NORTH)){
            firstz -= length;
            secondz++;
            secondx++;
        }
        else if (e.getPlayer().getFacing().equals(BlockFace.SOUTH)){
            secondz += length;
            firstz--;
            secondx++;
        }
        else if (e.getPlayer().getFacing().equals(BlockFace.EAST)){
            secondx += length;
            firstx--;
            secondz++;
        }
        else if (e.getPlayer().getFacing().equals(BlockFace.WEST)){
            firstx -= length;
            secondx++;
            secondz++;
        }

        for (int x = firstx; x < secondx; x++){
            for (int y = firsty; y < secondy; y++){
                for (int z = firstz; z < secondz; z++){
                    blockList.add(e.getPlayer().getWorld().getBlockAt(x,y,z));
                }
            }
        }
        blockList.remove(e.getBlock());
        return blockList;
    }
}

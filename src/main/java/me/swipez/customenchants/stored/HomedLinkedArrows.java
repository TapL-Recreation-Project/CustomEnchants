package me.swipez.customenchants.stored;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class HomedLinkedArrows {

    public static HashMap<UUID, UUID> storedHomingArrows = new HashMap<>();
    public static HashMap<UUID, LivingEntity> TargetedEntity = new HashMap<>();

    public static List<FallingBlock> battedBlocks = new ArrayList<>();

    public static UUID getArrowShooter (Arrow arrow){

        return storedHomingArrows.get(arrow.getUniqueId());
    }

}

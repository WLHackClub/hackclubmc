package io.github.wlhackclub.hackclubmc;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class GunListener implements Listener {

    private final Set<UUID> snowballs = new HashSet<>();

    @EventHandler()
    public void onClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_AIR && event.getAction() != Action.LEFT_CLICK_BLOCK) {
            return;
        }
        ItemStack item = event.getItem();
        if (item == null) {
            return;
        }
        PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();
        if (pdc.has(new NamespacedKey("hackclubmc", "gun"), PersistentDataType.INTEGER)) {
            Player player = event.getPlayer();
            Location spawnLocation = player.getEyeLocation().add(player.getEyeLocation().getDirection().multiply(0.5));
            Snowball snowball = (Snowball) player.getWorld().spawnEntity(spawnLocation, EntityType.SNOWBALL);
            snowball.setVelocity(player.getEyeLocation().getDirection().multiply(3.0));
            snowballs.add(snowball.getUniqueId());
        }
    }

    @EventHandler()
    public void onSnowballLand(ProjectileHitEvent event) {
        UUID idOfEntity = event.getEntity().getUniqueId();
        if (snowballs.contains(idOfEntity)) {
            snowballs.remove(idOfEntity);
            event.getEntity().getWorld().createExplosion(event.getEntity().getLocation(), 7.0F, true, true);
        }
    }

}

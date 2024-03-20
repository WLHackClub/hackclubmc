package io.github.wlhackclub.hackclubmc;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class GunListener implements Listener {

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
        }
    }

}

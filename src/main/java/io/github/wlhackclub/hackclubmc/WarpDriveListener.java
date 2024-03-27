package io.github.wlhackclub.hackclubmc;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class WarpDriveListener implements Listener {

    @EventHandler()
    public void onClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (event.getPlayer().isSneaking()) {
                ItemMeta im = event.getItem().getItemMeta();
                if (im.getPersistentDataContainer().has(new NamespacedKey("hackclubmc", "warp"), PersistentDataType.INTEGER)) {
                    World theEnd = Bukkit.getServer().getWorld("world_the_end");
                    if (theEnd != null) {
                        event.getPlayer().teleport(theEnd.getSpawnLocation());
                    }
                    event.getItem().setAmount(event.getItem().getAmount() - 1);
                }
            }
        }
    }

}

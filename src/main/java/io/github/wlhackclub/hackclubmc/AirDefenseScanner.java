package io.github.wlhackclub.hackclubmc;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class AirDefenseScanner extends BukkitRunnable {

    private final Main plugin;
    public AirDefenseScanner(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            for (Entity entity : player.getNearbyEntities(60.0, 10000.0, 60.0)) {
                if (!(entity instanceof ItemFrame itemFrame))  continue;
                if (itemFrame.getItem().getType() != Material.NETHERITE_SWORD)  continue;

                // TODO: spawn missiles

                player.getWorld().createExplosion(player.getLocation(), 4.0F, true, true);

            }
        }

    }

}

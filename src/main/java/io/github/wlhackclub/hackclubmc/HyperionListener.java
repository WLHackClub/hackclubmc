package io.github.wlhackclub.hackclubmc;

import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public class HyperionListener implements Listener {

    @EventHandler()
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = event.getItem();
            if (item == null) {
                return;
            }
            PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();
            if (pdc.has(new NamespacedKey("hackclubmc", "hyperion"), PersistentDataType.INTEGER)) {

                Player player = event.getPlayer();

                RayTraceResult result = event.getPlayer().getWorld().rayTraceBlocks(
                        player.getEyeLocation(),
                        player.getEyeLocation().getDirection(),
                        6.0,
                        FluidCollisionMode.NEVER,
                        true
                );
                if (result == null || result.getHitBlock() == null) {
                    Location newLocation = player.getEyeLocation().add(player.getEyeLocation().getDirection().multiply(6.0));
                    player.teleport(newLocation);
                } else {
                    Vector delta = result.getHitBlock().getLocation().subtract(player.getEyeLocation()).toVector();
                    double length = delta.length() - 1.1;
                    player.teleport(player.getEyeLocation().add(player.getEyeLocation().getDirection().multiply(length)));
                }

                event.getPlayer().damage(2.0);
                for (Entity entity : event.getPlayer().getNearbyEntities(5.0, 5.0, 5.0)) {
                    if (entity instanceof LivingEntity) {
                        ((LivingEntity) entity).damage(8.0, event.getPlayer());
                    }
                }

                // Create visual effects
                event.getPlayer().getWorld().spawnParticle(Particle.EXPLOSION_LARGE, event.getPlayer().getLocation(), 5);
                event.getPlayer().getWorld().playSound(event.getPlayer().getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.0F, 1.0F);

            }
        }
    }

}

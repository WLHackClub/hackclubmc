package io.github.wlhackclub.hackclubmc;

import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class HyperionListener implements Listener {

    @EventHandler()
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = event.getItem();
            if (item == null) {
                return;
            }
            PersistentDataContainer pdc = item.getItemMeta().getPersistentDataContainer();
            if (pdc.has(new NamespacedKey("hackclubmc", "gun"), PersistentDataType.INTEGER)) {

                // TODO: Teleport

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

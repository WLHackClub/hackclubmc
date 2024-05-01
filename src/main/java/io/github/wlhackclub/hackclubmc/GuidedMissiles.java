package io.github.wlhackclub.hackclubmc;

import jdk.jshell.execution.Util;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class GuidedMissiles {
    private final float BLOCK_EXPLOSION_POWER = 6.0F;
    private final float ENTITY_EXPLOSION_POWER = 19.0F;
    private final float WEAK_EXPLOSION_POWER = 5.0F;
    public static double PROXIMITY_DISTANCE_SQUARED = 6.5 * 6.5;
    // higher acceleration = faster missile
    public static double ACCELERATION = 0.63;
    // higher air resistance (lower number) = slower missile, but more maneuverable
    public static double AIR_RESISTANCE = 0.5;
    // when air resistance is applied before acceleration, terminal velocity is given by: a / (1-d)

    // There are cases where missiles can orbit the target
    // (if the missile gets the right amount of centripetal acceleration)
    // with a radius larger than the proximity distance.
    // This unfortunately isn't really avoidable, but it doesn't happen very often at the current settings.
    // A lower acceleration would decrease the turn radius making it more likely to go inside proximity distance and
    // explode (since r = v^2/a = a / (1-d)^2), and the same is true for more air resistance (lower d).
    // (That formula isn't perfect since some acceleration is wasted on centripetal force instead of all being used
    //  for keeping the missile at top speed, but it's close. Also, it can also do that in 3D, such as when
    //  a player is moving and the missile is moves in a corkscrew shape. Here the cross-sectional acceleration
    //  would be even lower.)
    // We can prevent this, in theory, by having r be less than the proximity distance, but that would be extremely
    // excessive.

    private final Main plugin;

    public GuidedMissiles(Main plugin) {
        this.plugin = plugin;
    }

    public void launch(Player target, Location spawnLoc) {
        World world = spawnLoc.getWorld();
        Fireball fireball = (Fireball) world.spawnEntity(spawnLoc, EntityType.FIREBALL);
        fireball.setVelocity(
                target.getLocation().subtract(spawnLoc).toVector().normalize().multiply(0.5)
        );
        fireball.setYield(WEAK_EXPLOSION_POWER);

        new BukkitRunnable() {
            int tickNumber = 0;

            public void run() {
                tickNumber++;
                if (fireball.isDead()) {
                    cancel();
                    return;
                }
                Location fireballLoc = fireball.getLocation();
                // smoke trail: we can't do this perfectly but let's try our best
                final int NUMBER_PARTICLES = 10;
                Vector displacement = fireball.getVelocity(); // the velocity was the displacement this tick
                for (int i = 0; i < NUMBER_PARTICLES; i++) {
                    // TODO: is the BukkitRunnable called BEFORE or AFTER the fireball is moved?
                    Location loc = fireballLoc.subtract(displacement.clone().multiply(i / (double) NUMBER_PARTICLES));
                    fireball.getWorld().spawnParticle(Particle.FLAME, loc, 5, 0.0, 0.0, 0.0, 0.05);
                    fireball.getWorld().spawnParticle(Particle.SMOKE_LARGE, loc, 2, 0.0, 0.0, 0.0, 0.025);
                }
                // proximity fuse
                if (fireballLoc.distanceSquared(target.getLocation()) < PROXIMITY_DISTANCE_SQUARED) {
                    fireball.getWorld().createExplosion(fireballLoc, BLOCK_EXPLOSION_POWER, true, true, fireball);
                    fireball.getWorld().createExplosion(fireballLoc, ENTITY_EXPLOSION_POWER, false, false, fireball);
                    target.damage(1_000_000.0, fireball);
                    fireball.remove();
                    cancel();
                    return;
                }
                // if target too far away, quit
                if (fireballLoc.distanceSquared(target.getLocation()) > 300.0 * 300.0) {
                    fireball.remove();
                    cancel();
                    return;
                }
                // update fireball's velocity
                target.sendMessage("velocity beginning: "+fireball.getVelocity().length());
                Vector desiredDirection = target.getLocation().subtract(fireballLoc).toVector().normalize();
                double realAcceleration = tickNumber >= 20 ? ACCELERATION : 0.05 * tickNumber * ACCELERATION; // slower at the beginning
                Vector newVelocity = fireball.getVelocity().multiply(AIR_RESISTANCE).add(desiredDirection.multiply(realAcceleration));
                fireball.setVelocity(newVelocity);
                target.sendMessage("velocity after: "+newVelocity.length());
            }
        }.runTaskTimer(plugin, 1L, 1L);
    }

}

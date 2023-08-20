package org.cubeville.cvpatreon;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class VentingSteamTask extends BukkitRunnable {

    Player player;
    int cnt;
    double xLast;
    double zLast;

    public VentingSteamTask(Player player) {
        this.player = player;
        cnt = 0;
        Location loc = player.getEyeLocation();
        xLast = loc.getX();
        zLast = loc.getZ();
        player.getWorld().getTime();
    }

    @Override
    public void run() {
        Location loc = player.getEyeLocation();
        // Estimate player velocity to compensate for motion.
        // Seems to have some wierd scaling.
        double xVelP = (loc.getX() - xLast) * 0.6;
        double zVelP = (loc.getZ() - zLast) * 0.6;
        xLast = loc.getX();
        zLast = loc.getZ();

        // Should really compensate for change in ear location due to head tilt,
        // but this is complicated enough already.

        if ((cnt < 60) && (cnt % 20 < 2)) {
            double bodyYaw = loc.getYaw();

            double yaw = bodyYaw + 90.0 + Math.random() * 20.0 - 10.0;
            loc.add(-sin(Math.toRadians(yaw)) * 0.3, 0.05, cos(Math.toRadians(yaw)) * 0.3);
            player.getWorld().spawnParticle(Particle.SMOKE_NORMAL, loc, 4, 0.02, 0.02, 0.02, 0.0);

            loc = player.getEyeLocation();
            yaw = bodyYaw - 90.0 + Math.random() * 20.0 - 10.0;
            loc.add(-sin(Math.toRadians(yaw)) * 0.3, 0.05, cos(Math.toRadians(yaw)) * 0.3);
            player.getWorld().spawnParticle(Particle.SMOKE_NORMAL, loc, 4, 0.02, 0.02, 0.02, 0.0);

            if (cnt % 20 == 0) {
                player.getWorld().playSound(loc, org.bukkit.Sound.BLOCK_LAVA_POP, SoundCategory.PLAYERS, 0.75f, 0.5f);
            }
        } else if ((cnt >= 60) && (cnt < 160)) {
            double bodyYaw = loc.getYaw();

            // Right ear
            double yaw = bodyYaw + 90.0 + Math.random() * 20.0 - 10.0;
            loc.add(-sin(Math.toRadians(yaw)) * 0.4, 0.05, cos(Math.toRadians(yaw)) * 0.4);
            double xDir = -sin(Math.toRadians(yaw));
            double yDir = Math.random() * 0.2 - 0.1;
            double zDir = cos(Math.toRadians(yaw));
            double vel = Math.random() * 0.01 + 0.065;
            if (Math.random() <= 0.1) {
                player.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, loc, 0, xDir, yDir, zDir, vel * 0.35);
            } else if (Math.random() <= 0.2) {
                player.getWorld().spawnParticle(Particle.SMOKE_NORMAL, loc, 0, xDir, yDir, zDir, vel * 0.15);
            }
            xDir *= vel;
            yDir *= vel;
            zDir *= vel;
            xDir += xVelP;
            zDir += zVelP;
            vel = Math.sqrt(xDir*xDir + yDir*yDir + zDir*zDir);
            xDir /= vel;
            yDir /= vel;
            zDir /= vel;
            player.getWorld().spawnParticle(Particle.END_ROD, loc, 0, xDir, yDir, zDir, vel * 2);

            // Left ear
            loc = player.getEyeLocation();
            yaw = bodyYaw - 90.0 + Math.random() * 20.0 - 10.0;
            loc.add(-sin(Math.toRadians(yaw)) * 0.4, 0.05, cos(Math.toRadians(yaw)) * 0.4);

            vel = Math.random() * 0.01 + 0.065;
            xDir = -sin(Math.toRadians(yaw));
            yDir = Math.random() * 0.2 - 0.1;
            zDir = cos(Math.toRadians(yaw));

            if (Math.random() <= 0.1) {
                player.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, loc, 0, xDir, yDir, zDir, vel * 0.35);
            } else if (Math.random() <= 0.2) {
                player.getWorld().spawnParticle(Particle.SMOKE_NORMAL, loc, 0, xDir, yDir, zDir, vel * 0.15);
            }
            xDir *= vel;
            yDir *= vel;
            zDir *= vel;
            xDir += xVelP;
            zDir += zVelP;
            vel = Math.sqrt(xDir*xDir + yDir*yDir + zDir*zDir);
            xDir /= vel;
            yDir /= vel;
            zDir /= vel;
            player.getWorld().spawnParticle(Particle.END_ROD, loc, 0, xDir, yDir, zDir, vel * 2);

            // Play the sound at random intervals
            if ((cnt == 60) || (Math.random() <= 0.2)) {
                player.getWorld().playSound(loc, Sound.ENTITY_GHAST_SCREAM, SoundCategory.PLAYERS, 1.0f, 1.0f);
            }
        }
        else if (cnt == 190) {
            player.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, loc, 100, 0.2, 0.2, 0.2, 0.0);
            player.getWorld().playSound(loc, org.bukkit.Sound.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 1.0f, 2.0f);
            this.cancel();
        }
        cnt++;
    }
}

package org.cubeville.cvpatreon;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.cubeville.commons.commands.Command;
import org.cubeville.commons.commands.CommandResponse;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class VentingSteam extends Command {

    JavaPlugin plugin;

    public VentingSteam(JavaPlugin plugin) {
        super("venting");
        this.plugin = plugin;
    }

    @Override
    public CommandResponse execute(Player player, Set<String> flags, Map<String, Object> parameters, List<Object> baseParameters) {
        BukkitTask task = new VentingSteamTask(player).runTaskTimer(this.plugin, 0, 1);

        return new CommandResponse("");
    }
}

package org.cubeville.cvpatreon;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.cubeville.commons.commands.CommandParser;

public class CVPatreon extends JavaPlugin implements Listener {

    private CommandParser commandParser;

    public void onEnable() {
        commandParser = new CommandParser();
        commandParser.addCommand(new VentingSteam(this));

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this, this);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equals("cvp")) {
            return commandParser.execute(sender, args);
        }
        return false;
    }

}

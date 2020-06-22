package de.marvinleiers.ccplugin.commands;

import de.marvinleiers.ccplugin.CCPlugin;
import de.marvinleiers.ccplugin.user.User;
import de.marvinleiers.ccplugin.utils.Locations;
import de.marvinleiers.ccplugin.utils.Messages;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor
{
    private CCPlugin plugin = CCPlugin.plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage(Messages.get("only-players"));
            return true;
        }

        Player player = (Player) sender;
        User user = plugin.getUser(player);

        if (args.length == 0)
        {
            //TODO: send help
            return true;
        }

        if (args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("home"))
        {
            if (!user.hasIsland())
            {
                Location loc = new Location(CCPlugin.getWorld(), 0.5, 75, 0.5);

                if (plugin.getConfig().isSet("next-island"))
                    loc = Locations.get("next-island");

                if (loc.clone().subtract(0, 1, 0).getBlock().getType() == Material.AIR)
                    loc.clone().subtract(0, 1, 0).getBlock().setType(Material.GRASS_BLOCK);

                player.teleport(loc);

                user.getConfig().set("island.x", loc.getX());
                user.getConfig().set("island.y", loc.getY());
                user.getConfig().set("island.z", loc.getZ());
                user.saveConfig();

                player.sendMessage(Messages.get("created"));

                Locations.setLocation("next-island", loc.clone().add(5000, 0, 5000));
            }
            else
            {
                player.teleport(new Location(CCPlugin.getWorld(), user.getConfig().getDouble("island.x"),
                        user.getConfig().getDouble("island.y"), user.getConfig().getDouble("island.z"),
                        player.getLocation().getYaw(), player.getLocation().getPitch()));

                player.sendMessage(Messages.get("welcome-home"));
            }
        }

        return true;
    }
}

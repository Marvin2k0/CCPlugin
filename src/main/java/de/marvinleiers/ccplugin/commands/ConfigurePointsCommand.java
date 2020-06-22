package de.marvinleiers.ccplugin.commands;

import de.marvinleiers.ccplugin.CCPlugin;
import de.marvinleiers.ccplugin.utils.Messages;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ConfigurePointsCommand implements CommandExecutor
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

        if (args.length < 1)
        {
            player.sendMessage("§cUsage: /ccpoints <points>");
            return true;
        }

        if (player.getInventory().getItemInMainHand() != null && player.getInventory().getItemInMainHand().getType() != Material.AIR && player.getInventory().getItemInMainHand().getType().isBlock())
        {
            String block = player.getInventory().getItemInMainHand().getType().toString();

            try
            {
                int points = Integer.parseInt(args[0]);

                plugin.getConfig().set("blocks." + block + ".points", points);
                plugin.saveConfig();

                CCPlugin.blocks.put(Material.getMaterial(block), points);

                player.sendMessage(Messages.get("new-block-value").replace("%block%", block).replace("%value%", points + ""));
            }
            catch (NumberFormatException e)
            {
                player.sendMessage(Messages.get("num-error"));
            }

            return true;
        }
        else
        {
            player.sendMessage(Messages.get("null-item"));
            return true;
        }
    }
}

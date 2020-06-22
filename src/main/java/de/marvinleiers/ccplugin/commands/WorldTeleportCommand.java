package de.marvinleiers.ccplugin.commands;

import de.marvinleiers.ccplugin.CCPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldTeleportCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!(sender instanceof Player))
        {
            sender.sendMessage("§cThis command is only for players!");
            return true;
        }

        Player player = (Player) sender;

        player.teleport(Bukkit.getWorld(CCPlugin.WORLD_NAME).getSpawnLocation());

        return true;
    }
}

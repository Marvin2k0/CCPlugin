package de.marvinleiers.ccplugin.listeners;

import de.marvinleiers.ccplugin.CCPlugin;
import de.marvinleiers.ccplugin.user.User;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BuildListener implements Listener
{
    private CCPlugin plugin = CCPlugin.plugin;

    @EventHandler
    public void onBuild(BlockPlaceEvent event)
    {
        Player player = event.getPlayer();
        User user = plugin.getUser(player);

        if (event.isCancelled())
            return;

        if (!user.hasIsland())
            return;

        if (event.getBlock().getLocation().distance(user.getIsland()) > CCPlugin.BORDER / 2)
        {
            event.setCancelled(true);
            return;
        }

        Block block = event.getBlock();

        if (CCPlugin.blocks.containsKey(block.getType()))
        {
            int points = CCPlugin.blocks.get(block.getType());

            System.out.println("adding " + points);
            user.addPoints(points);

            System.out.println(user.getPoints());
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event)
    {
        Player player = event.getPlayer();
        User user = plugin.getUser(player);

        if (event.isCancelled())
            return;

        if (!user.hasIsland())
            return;

        if (event.getBlock().getLocation().distance(user.getIsland()) > CCPlugin.BORDER / 2)
            event.setCancelled(true);
    }
}

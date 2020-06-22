package de.marvinleiers.ccplugin;

import de.marvinleiers.ccplugin.commands.MainCommand;
import de.marvinleiers.ccplugin.commands.WorldTeleportCommand;
import de.marvinleiers.ccplugin.user.User;
import de.marvinleiers.ccplugin.utils.Locations;
import de.marvinleiers.ccplugin.utils.Messages;
import de.marvinleiers.ccplugin.world.SkyWorldGenerator;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.logging.Level;

public final class CCPlugin extends JavaPlugin implements Listener
{
    public static HashMap<Player, User> users = new HashMap<>();
    public static final String WORLD_NAME = "ccplugin_world";
    private static World skyblockWorld = null;
    public static CCPlugin plugin;

    @Override
    public void onEnable()
    {
        plugin = this;

        Messages.setUp(this);
        Locations.setUp(this);

        this.getCommand("cctp").setExecutor(new WorldTeleportCommand());
        this.getCommand("cc").setExecutor(new MainCommand());

        this.getServer().getPluginManager().registerEvents(this, this);
        this.getServer().getPluginManager().registerEvents(new AntiCooldown(), this);

        this.createSkyblockWorld();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        users.put(player, new User(player));
    }

    public User getUser(Player player)
    {
        return users.get(player);
    }

    public static World getWorld()
    {
        return skyblockWorld;
    }

    private void createSkyblockWorld()
    {
        Bukkit.getLogger().log(Level.INFO, "Loading world...");

        if (Bukkit.getWorld(WORLD_NAME) == null)
        {
            WorldCreator worldCreator = new WorldCreator(WORLD_NAME);
            worldCreator.generateStructures(false);
            worldCreator.generator(new SkyWorldGenerator());
            Bukkit.getServer().createWorld(worldCreator);
        }

        skyblockWorld = Bukkit.getWorld(WORLD_NAME);
        skyblockWorld.setSpawnLocation(0, 75, 0);
        skyblockWorld.setAutoSave(true);

        System.out.println(skyblockWorld.getSpawnLocation().toString());
    }
}

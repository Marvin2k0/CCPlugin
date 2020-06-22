package de.marvinleiers.ccplugin;

import de.marvinleiers.ccplugin.anticooldown.AntiCooldown;
import de.marvinleiers.ccplugin.commands.ConfigurePointsCommand;
import de.marvinleiers.ccplugin.commands.MainCommand;
import de.marvinleiers.ccplugin.commands.WorldTeleportCommand;
import de.marvinleiers.ccplugin.listeners.BuildListener;
import de.marvinleiers.ccplugin.user.User;
import de.marvinleiers.ccplugin.utils.Locations;
import de.marvinleiers.ccplugin.utils.Messages;
import de.marvinleiers.ccplugin.world.SkyWorldGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public final class CCPlugin extends JavaPlugin implements Listener
{
    private static HashMap<Player, User> users = new HashMap<>();
    public static HashMap<Material, Integer> blocks = new HashMap<>();
    public static final String WORLD_NAME = "ccplugin_world";
    public static final int BORDER = 5000;
    private static World skyblockWorld = null;
    public static CCPlugin plugin;

    @Override
    public void onEnable()
    {
        plugin = this;

        Messages.setUp(this);
        Locations.setUp(this);

        this.getCommand("ccpoints").setExecutor(new ConfigurePointsCommand());
        this.getCommand("cctp").setExecutor(new WorldTeleportCommand());
        this.getCommand("cc").setExecutor(new MainCommand());

        this.getServer().getPluginManager().registerEvents(this, this);
        this.getServer().getPluginManager().registerEvents(new AntiCooldown(), this);
        this.getServer().getPluginManager().registerEvents(new BuildListener(), this);

        this.createSkyblockWorld();
        this.loadBlockValues();
        this.registerOnlineUsers();
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
    }

    private void loadBlockValues()
    {
        if (!getConfig().isSet("blocks"))
            return;

        for (Map.Entry<String, Object> entry : getConfig().getConfigurationSection("blocks").getValues(false).entrySet())
        {
            Material material = Material.getMaterial(entry.getKey());
            int points = getConfig().getInt("blocks." + entry.getKey() + ".points");

            System.out.println("Adding " + material.toString() + ", value = " + points);
            blocks.put(material, points);
        }
    }

    private void registerOnlineUsers()
    {
        for (Player player : Bukkit.getOnlinePlayers())
            users.put(player, new User(player));
    }
}

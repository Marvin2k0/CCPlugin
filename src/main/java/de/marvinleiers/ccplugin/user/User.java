package de.marvinleiers.ccplugin.user;

import de.marvinleiers.ccplugin.CCPlugin;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class User
{
    private static CCPlugin plugin = CCPlugin.plugin;

    private File file;
    private FileConfiguration config;
    private Player player;
    private int points;

    public User(Player player)
    {
        this.player = player;
        this.file = new File(plugin.getDataFolder().getPath() + "/users/" + player.getUniqueId().toString() + ".yml");
        this.config = YamlConfiguration.loadConfiguration(file);
        this.saveConfig();

        this.points = 0;

        if (config.isSet("points"))
            this.points = config.getInt("points");
    }

    public void addPoints(int points)
    {
        this.points += points;
        this.config.set("points", points);
        this.saveConfig();
    }

    public int getPoints()
    {
        return this.points;
    }

    public boolean hasIsland()
    {
        return config.isSet("island");
    }

    public Location getIsland()
    {
        if (hasIsland())
        {
            return new Location(CCPlugin.getWorld(), getConfig().getDouble("island.x"),
                    getConfig().getDouble("island.y"), getConfig().getDouble("island.z"),
                    player.getLocation().getYaw(), player.getLocation().getPitch());
        }

        return null;
    }

    public FileConfiguration getConfig()
    {
        return config;
    }

    public void saveConfig()
    {
        try
        {
            config.save(file);
        }
        catch (IOException ignored)
        {
        }
    }

    public Player getPlayer()
    {
        return player;
    }
}

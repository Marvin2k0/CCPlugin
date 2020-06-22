package de.marvinleiers.ccplugin.user;

import de.marvinleiers.ccplugin.CCPlugin;
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

    public User(Player player)
    {
        this.player = player;
        this.file = new File(plugin.getDataFolder().getPath() + "/users/" + player.getUniqueId().toString() + ".yml");
        this.config = YamlConfiguration.loadConfiguration(file);
        this.saveConfig();
    }

    public boolean hasIsland()
    {
        return config.isSet("island");
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
        catch (IOException ignored) {}
    }

    public Player getPlayer()
    {
        return player;
    }
}

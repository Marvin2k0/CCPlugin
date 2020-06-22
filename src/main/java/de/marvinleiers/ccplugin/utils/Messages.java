package de.marvinleiers.ccplugin.utils;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Messages
{
    private static FileConfiguration config;
    private static JavaPlugin plugin;

    public static String get(String path)
    {
        return ChatColor.translateAlternateColorCodes('&', path.equalsIgnoreCase("prefix") ? config.getString("prefix") : config.getString("prefix") + " " + config.getString(path));
    }

    private static void addDefaults()
    {
        config.options().copyDefaults(true);

        config.addDefault("prefix", "&6[Plugin]&f");
        config.addDefault("only-players", "&cThis command is only for players!");
        config.addDefault("created", "&7Island successfully created!");
        config.addDefault("welcome-home", "&7You are now on your island!");
        config.addDefault("null-item", "&cThis item is not valid!");
        config.addDefault("num-error", "&cPlease enter numbers only!");
        config.addDefault("new-block-value", "&7Block &e%block% &7has now the value &e%value%");

        plugin.saveConfig();
    }

    public static void setUp(JavaPlugin plugin)
    {
        Messages.plugin = plugin;

        config = plugin.getConfig();

        addDefaults();
    }
}

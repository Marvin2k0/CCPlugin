package de.marvinleiers.ccplugin.anticooldown;

import de.marvinleiers.ccplugin.CCPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

public final class AntiCooldown implements Listener
{
    private static final HashMap<Player, ItemStack> list = new HashMap<>();

    @EventHandler
    public void onJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();

        adjustCooldown(player);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event)
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                adjustCooldown(event.getPlayer());
            }
        }.runTaskLater(CCPlugin.plugin, 1);
    }

    @EventHandler
    public void onBlock(PlayerInteractEvent event)
    {
        if (!event.hasItem())
            return;

        if (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR)
            return;

        ItemStack item = event.getItem();
        Player player = event.getPlayer();

        if (isWeapon(item.getType()))
        {
            if (player.getInventory().getItem(40) == null || player.getInventory().getItem(40).getType() != Material.SHIELD)
            {
                System.out.println("added");
                list.put(player, player.getInventory().getItem(40));
            }

            player.getInventory().setItem(40, new ItemStack(Material.SHIELD));

            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    if (list.containsKey(player) && !player.isBlocking())
                    {
                        player.getInventory().setItem(40, list.get(player));
                        this.cancel();
                    }
                }
            }.runTaskTimer(CCPlugin.plugin, 10, 1);
        }
    }

    private void adjustCooldown(Player player)
    {
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(100);
    }

    private boolean isWeapon(Material type)
    {
        return type.toString().contains("AXE") || type.toString().contains("SWORD");
    }
}

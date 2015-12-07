package me.vik1395.repair;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.meta.ItemMeta;

/*

Author: Vik1395
Project: NoLoreRepair

Copyright 2015

Licensed under Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International Public License (the "License");
You may not use this file except in compliance with the License.

You may obtain a copy of the License at http://creativecommons.org/licenses/by-nc-sa/4.0/legalcode

You may find an abridged version of the License at http://creativecommons.org/licenses/by-nc-sa/4.0/
 */

public class ListenerClass implements Listener 
{
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e)
	{
		if(e.getInventory() instanceof AnvilInventory && e.getWhoClicked() instanceof Player)
		{
			Player p = (Player)e.getWhoClicked();
			if(e.getCurrentItem()!=null)
			{
				if(e.getCurrentItem().hasItemMeta())
				{
					ItemMeta meta = e.getCurrentItem().getItemMeta();
					if(meta.hasLore())
					{
						List<String> lore = meta.getLore();
						
						for(int i = 0; i < Main.lores.length; i++)
						{
							if(Main.blockall)
							{
								if(p.hasPermission("repair.nolore") || p.hasPermission("norepair.nolore"))
								{
									p.sendMessage(ChatColor.RED + "Error: " + ChatColor.DARK_RED + "This item cannot be repaired.");
									e.setCancelled(true);
									break;
								}
							}
							else if(lore.contains(Main.lores[i]))
							{
								if(p.hasPermission("repair.nolore") || p.hasPermission("norepair.nolore"))
								{
									p.sendMessage(ChatColor.RED + "Error: " + ChatColor.DARK_RED + "This item cannot be repaired.");
									e.setCancelled(true);
									break;
								}
							}
						}
					}
				}
			}
		}
	}
}

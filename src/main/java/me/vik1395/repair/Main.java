package me.vik1395.repair;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/*

Author: Vik1395
Project: NoLoreRepair

Copyright 2015

Licensed under Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International Public License (the "License");
You may not use this file except in compliance with the License.

You may obtain a copy of the License at http://creativecommons.org/licenses/by-nc-sa/4.0/legalcode

You may find an abridged version of the License at http://creativecommons.org/licenses/by-nc-sa/4.0/
 */

public class Main extends JavaPlugin {

	private static HashMap<String, Date> delaymap = new HashMap<String, Date>();
	
	public void onEnable()
	{
		String loresraw = getConfig().getString("Lores");
		blockall = getConfig().getBoolean("Block All Lores");
		blockanvil = getConfig().getBoolean("Block Anvil Repair");
		msg = getConfig().getString("Message");
		delay = Integer.parseInt(getConfig().getString("Delay"));
		getLogger().info("NoLoreRepair has successfully started!");
		getLogger().info("Created by Vik1395");
		saveDefaultConfig();
		
		if(blockanvil)
		{
			System.out.println("works");
			Bukkit.getPluginManager().registerEvents(new ListenerClass(), this);
		}
		
		if(loresraw.contains(";"))
		{
			lores = loresraw.split(";");
		}
	}
	

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args)
	{
		
		if(cmd.getName().equalsIgnoreCase("repair"))
		{
			if(s instanceof Player)
			{
				Player m = (Player)s;
				
				if(m.hasPermission("repair.admin"))
				{
					String norepair = ChatColor.RED + "Error: " + ChatColor.DARK_RED + "This item cannot be repaired";
					
					if(args.length == 0 || (args.length >=1 && args[0].equalsIgnoreCase("hand")))
					{
						Material material = m.getItemInHand().getType();
						if (material.isBlock() || material.getMaxDurability() < 1 || m.getItemInHand().getDurability() == 0)
						{
							m.sendMessage(norepair);
						}
						else
						{
							m.getItemInHand().setDurability((short) 0);
							m.sendMessage(ChatColor.GOLD + "You have successfully repaired your: " + ChatColor.RED + m.getItemInHand().getType().toString().toLowerCase(Locale.ENGLISH).replace('_', ' '));
						}
					}
					
					else if(args[0].equalsIgnoreCase("all"))
					{
						String itemlist = "";
						for (ItemStack item : m.getInventory())
						{
							if(item != null)
							{
								Material material = item.getType();
								if (material.isBlock() || material.getMaxDurability() < 1 || item.getDurability() == 0)
								{
									
								}
								else
								{
									item.setDurability((short) 0);
									itemlist = itemlist + item.getType().toString().toLowerCase(Locale.ENGLISH).replace('_', ' ') + ", ";
									
								}
								
							}
						}
							m.sendMessage(itemlist);
					}
				}
				
				else if(m.hasPermission("repair.nolore") || m.hasPermission("norepair.nolore"))
				{
					if(delaymap.containsKey(m.getName()))
					{
						Date d = delaymap.get(m.getName());
						long differenceInMin = DelayCheck(d);
						
						if(differenceInMin<delay)
					    {
							int time = (int) (delay-differenceInMin);
							m.sendMessage(ChatColor.RED+"You need to wait for " + time + " more minutes before you can use this command again.");
					    }
					    else
					    {
					    	RepairCheck(args, m);
					    	delaymap.remove(m.getName());
					    }
					}
					else
					{
						RepairCheck(args, m);
					}
				}
				
				else
				{
					m.sendMessage(ChatColor.DARK_RED + "You are not allowed to use this command");
				}
				
			}
		}
		return true;
	}
	
	private void RepairCheck(String[] args, Player m)
	{
		int num = 0;
		short dur = 0;
		String norepair = ChatColor.RED + "Error: " + ChatColor.DARK_RED + "This item cannot be repaired";
		
		if(args.length == 0 || (args.length >=1 && args[0].equalsIgnoreCase("hand")))
		{
			Material material = m.getItemInHand().getType();
			if(m.getInventory().getItemInHand().getType()!=null)
			{
				if(m.getInventory().getItemInHand().getItemMeta().hasLore()) 
				{
					if(blockall)
					{
						m.sendMessage(ChatColor.RED + "You are not allowed to repair items with lore.");
					}
					else
					{
						List<String> lore = m.getInventory().getItemInHand().getItemMeta().getLore();
						
						for(int i = 0; i < lores.length; i++)
						{
							if(lore.contains(lores[i]))
							{
								m.sendMessage(ChatColor.RED + msg);
							}
							else
							{
								if (material.isBlock() || material.getMaxDurability() < 1 || m.getItemInHand().getDurability() == 0)
								{
									m.sendMessage(norepair);
								}
								else
								{
									m.getItemInHand().setDurability(dur);
									m.sendMessage(ChatColor.GOLD + "You have successfully repaired your: " + ChatColor.RED + m.getItemInHand().getType().toString().toLowerCase(Locale.ENGLISH).replace('_', ' '));
									delaymap.put(m.getName(), Calendar.getInstance().getTime());
								}
							}
						}
					}
				}
				else
				{
					if (material.isBlock() || material.getMaxDurability() < 1 || m.getItemInHand().getDurability() == 0)
					{
						m.sendMessage(norepair);
					}
					else
					{
						m.getItemInHand().setDurability(dur);
						m.sendMessage(ChatColor.GOLD + "You have successfully repaired your: " + ChatColor.RED + m.getItemInHand().getType().toString().toLowerCase(Locale.ENGLISH).replace('_', ' '));
						delaymap.put(m.getName(), Calendar.getInstance().getTime());
					}
				}
			}
			else
			{
				m.sendMessage(norepair);
			}
		}
		else if(args[0].equalsIgnoreCase("all"))
		{
			num = 0;
			String itemlist = "";
			boolean repaircheck = true;
			for (ItemStack item : m.getInventory())
			{
				if(item != null)
				{
					Material material = item.getType();
					if(item.hasItemMeta()&&!blockall)
					{
						
						if(item.getItemMeta().hasLore()) 
						{
							List<String> lore = m.getInventory().getItemInHand().getItemMeta().getLore();
							
							for(int i = 0; i < lores.length; i++)
							{
								if(lore.contains(lores[i]))
								{
									num = num - 1;
								}
								else
								{
									if (material.isBlock() || material.getMaxDurability() < 1 || m.getItemInHand().getDurability() == 0)
									{
										
									}
									else
									{
										item.setDurability(dur);
										num = num + 1;
										itemlist = itemlist + item.getType().toString().toLowerCase(Locale.ENGLISH).replace('_', ' ') + ", ";
										
									}
								}
							}
						}
						else
						{
							if (material.isBlock() || material.getMaxDurability() < 1 || m.getItemInHand().getDurability() == 0)
							{
								
							}
							else
							{
								item.setDurability(dur);
								num = num + 1;
								itemlist = itemlist + item.getType().toString().toLowerCase(Locale.ENGLISH).replace('_', ' ') + ", ";
								
							}
						}
						
					}
				
					else
					{
						if (material.isBlock() || material.getMaxDurability() < 1 || m.getItemInHand().getDurability() == 0)
						{
							
						}
						else
						{
							item.setDurability(dur);
							num = num + 1;
							itemlist = itemlist + item.getType().toString().toLowerCase(Locale.ENGLISH).replace('_', ' ') + ", ";
							
						}
					}
				}
			}
			if(repaircheck == false)
			{
				m.sendMessage(norepair);
			}
			if(num<=0)
			{
				m.sendMessage(ChatColor.RED + "No Item in your inventory can be repaired");
			}
			
			else
			{
				if (itemlist.length() > 0 && itemlist.charAt(itemlist.length()-2)==',') 
				{
					itemlist = itemlist.substring(0, itemlist.length()-2);
				}
				m.sendMessage(ChatColor.GOLD + "You have successfully repaired your: " + ChatColor.RED + itemlist);
				delaymap.put(m.getName(), Calendar.getInstance().getTime());
			}
		}
		else
		{
			m.sendMessage(ChatColor.DARK_RED + "Usage: " + ChatColor.RED + "/repair hand, /repair all, /repair");
			m.sendMessage(ChatColor.DARK_RED + "Aliases: " + ChatColor.RED + "/repair, /rp, /fix");
		}
	}
	
	private long DelayCheck(Date date)
	{
	    Date now = Calendar.getInstance().getTime(); // Get time now
	    long differenceInMillis = now.getTime() - date.getTime();
	    long differenceInMin = (differenceInMillis) / 1000L / 60L;
	    
		return differenceInMin;
	}
	
	public static String[] lores;
	public static boolean blockall;
	private boolean blockanvil;
	private String msg;
	private int delay;

}
	


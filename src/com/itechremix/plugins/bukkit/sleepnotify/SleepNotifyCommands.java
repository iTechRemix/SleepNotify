package com.itechremix.plugins.bukkit.sleepnotify;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SleepNotifyCommands implements CommandExecutor
{
  SleepNotify plugin;
  int onlineNum = Bukkit.getServer().getOnlinePlayers().size();
  
  public SleepNotifyCommands(SleepNotify instance)
  {
    this.plugin = instance;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
  {
    StringBuilder sleepingNames = new StringBuilder();
    StringBuilder awakeNames = new StringBuilder();
    if (commandLabel.equalsIgnoreCase("sleeping")) {
      if (sender.hasPermission("sleepnotify.list"))
      {
        if (args.length == 0)
        {
          if (SleepNotifyListener.sleeping.size() == 0)
          {
            sender.sendMessage(ChatColor.AQUA + "No players are currently sleeping...");
          }
          else
          {
            sender.sendMessage(ChatColor.YELLOW + "Currently," + ChatColor.LIGHT_PURPLE + " " + SleepNotifyListener.sleeping.size() + " " + ChatColor.YELLOW + "out of" + ChatColor.LIGHT_PURPLE + " " + Bukkit.getServer().getOnlinePlayers().size() + ChatColor.YELLOW + " " + "players are sleeping:");
            for (String s : SleepNotifyListener.sleeping) {
              sleepingNames.append(s + "," + " ");
            }
            sender.sendMessage(sleepingNames.toString());
          }
          return true;
        }
      }
      else {
        sender.sendMessage(ChatColor.RED + "You don't have permission to use that command!");
      }
    }
    if (commandLabel.equalsIgnoreCase("awake")) {
      if (sender.hasPermission("sleepnotify.list"))
      {
        if (args.length == 0)
        {
          if (SleepNotifyListener.awake.size() == 0)
          {
            sender.sendMessage(ChatColor.AQUA + "No players are currently awake...");
          }
          else
          {
            sender.sendMessage(ChatColor.YELLOW + "Currently," + ChatColor.LIGHT_PURPLE + " " + SleepNotifyListener.awake.size() + " " + ChatColor.YELLOW + "out of" + ChatColor.LIGHT_PURPLE + " " + Bukkit.getServer().getOnlinePlayers().size() + ChatColor.YELLOW + " " + "players are awake:");
            for (String s : SleepNotifyListener.awake) {
              awakeNames.append(s + "," + " ");
            }
            sender.sendMessage(awakeNames.toString());
          }
          return true;
        }
      }
      else {
        sender.sendMessage(ChatColor.RED + "You don't have permission to use that command!");
      }
    }
    return false;
  }
}

package com.itechremix.plugins.bukkit.sleepnotify;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SleepNotifyConfigCommands implements CommandExecutor
{
  public String[] validChatColors = { "AQUA", "BLACK", "BLUE", "DARK_AQUA", "DARK_BLUE", "DARK_GRAY", "DARK_GREEN", "DARK_PURPLE", "DARK_RED", "GOLD", "GRAY", "GREEN", "LIGHT_PURPLE", "MAGIC", "RED", "WHITE", "YELLOW" };
  public StringBuilder validColors = new StringBuilder();
  SleepNotify plugin;
  
  public SleepNotifyConfigCommands(SleepNotify instance)
  {
    this.plugin = instance;
    for (String color : this.validChatColors) {
      this.validColors.append(color).append(", ");
    }
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
  {
    if ((commandLabel.equalsIgnoreCase("sn")) || (commandLabel.equalsIgnoreCase("sleepnotify")))
    {
      boolean kickPlayers = this.plugin.getConfig().getBoolean("Kick Awake Players If At Least Half of Players Are Asleep");
      boolean useNicks = this.plugin.getConfig().getBoolean("Use Nicknames");
      boolean broadcastMsg = this.plugin.getConfig().getBoolean("Broadcast Messages");
      if (args.length == 0)
      {
        if ((sender.hasPermission("sleepnotify.admin")) || (sender.isOp())) {
          sender.sendMessage(ChatColor.LIGHT_PURPLE + "Arguments: /sn [msgcolor | namecolor | usenicks | showop | autokick | broadcastmsg | about]");
        }
        return true;
      }
      if ((args[0].equalsIgnoreCase("msgcolor")) && ((sender.hasPermission("sleepnotify.admin")) || (sender.isOp())))
      {
        if (args.length == 1)
        {
          sender.sendMessage(ChatColor.LIGHT_PURPLE + "[SleepNotify] Current Message Color: " + ChatColor.valueOf(this.plugin.getConfig().getString("Message Color")) + this.plugin.getConfig().getString("Message Color"));
          sender.sendMessage(ChatColor.LIGHT_PURPLE + "[SleepNotify] To set, type /sn msgcolor set [color]");
        }
        if (args.length == 2) {
          sender.sendMessage("[SleepNotify] Usage: /sn msgcolor set [color]");
        }
        if ((args.length == 3) && (args[1].equalsIgnoreCase("set"))) {
          if (containsIgnoreCase(this.validChatColors, args[2]))
          {
            this.plugin.getConfig().set("Message Color", args[2].toUpperCase());
            sender.sendMessage(ChatColor.GREEN + "[SleepNotify] Message color successfully set to " + args[2] + "!");
          }
          else if (args[2].contentEquals("?"))
          {
            sender.sendMessage("[SleepNotify] Color Options: " + this.validColors.toString());
          }
          else
          {
            sender.sendMessage(ChatColor.RED + "[SleepNotify] Invalid color given! For a list of valid colors, type '/sn msgcolor set ?'");
          }
        }
        return true;
      }
      if ((args[0].equalsIgnoreCase("namecolor")) && ((sender.hasPermission("sleepnotify.admin")) || (sender.isOp())))
      {
        if (args.length == 1)
        {
          sender.sendMessage(ChatColor.LIGHT_PURPLE + "[SleepNotify] Current Name Color: " + ChatColor.valueOf(this.plugin.getConfig().getString("Player Name Color")) + this.plugin.getConfig().getString("Player Name Color"));
          sender.sendMessage(ChatColor.LIGHT_PURPLE + "[SleepNotify] To set, type /sn namecolor set [color]");
        }
        if (args.length == 2) {
          sender.sendMessage("[SleepNotify] Usage: /sn namecolor set [color]");
        }
        if ((args.length == 3) && (args[1].equalsIgnoreCase("set"))) {
          if (containsIgnoreCase(this.validChatColors, args[2]))
          {
            this.plugin.getConfig().set("Player Name Color", args[2].toUpperCase());
            sender.sendMessage(ChatColor.GREEN + "[SleepNotify] Name color successfully set to " + args[2] + "!");
          }
          else if (args[2].contentEquals("?"))
          {
            sender.sendMessage("[SleepNotify] Color Options: " + this.validColors.toString());
          }
          else
          {
            sender.sendMessage(ChatColor.RED + "[SleepNotify] Invalid color given! For a list of valid colors, type '/sn namecolor ?'");
          }
        }
        return true;
      }
      if (args[0].equalsIgnoreCase("usenicks"))
      {
        if ((sender.hasPermission("sleepnotify.admin")) || (sender.isOp()))
        {
          if (args.length == 1)
          {
            if (useNicks)
            {
              sender.sendMessage(ChatColor.LIGHT_PURPLE + "[SleepNotify] Nicknames are currently being used.");
              sender.sendMessage(ChatColor.LIGHT_PURPLE + "[SleepNotify] To change, type /sn usenicks [enable/disable]");
            }
            else
            {
              sender.sendMessage(ChatColor.LIGHT_PURPLE + "[SleepNotify] Nicknames are not being used currently.");
              sender.sendMessage(ChatColor.LIGHT_PURPLE + "[SleepNotify] To change, type /sn usenicks [enable/disable]");
            }
          }
          else if (args.length == 2)
          {
            if ((args[1].equalsIgnoreCase("enable")) || (args[1].equalsIgnoreCase("true"))) {
              if (!useNicks) {
                this.plugin.getConfig().set("Use Nicknames", true);
              } else {
                sender.sendMessage(ChatColor.RED + "[SleepNotify] Nicknames are already being used!");
              }
            }
            if ((args[1].equalsIgnoreCase("disable")) || (args[1].equalsIgnoreCase("false"))) {
              if (useNicks) {
                this.plugin.getConfig().set("Use Nicknames", false);
              } else {
                sender.sendMessage(ChatColor.RED + "[SleepNotify] Nicknames are already disabled!");
              }
            }
          }
        }
        else {
          sender.sendMessage(ChatColor.RED + "You don't have permission to perform that command!");
        }
        return true;
      }
      if (args[0].equalsIgnoreCase("showop"))
      {
        if ((sender.hasPermission("sleepnotify.admin")) || (sender.isOp()))
        {
          if (args.length == 1)
          {
            if (this.plugin.getConfig().getBoolean("Show Alerts for OPs"))
            {
              sender.sendMessage(ChatColor.LIGHT_PURPLE + "[SleepNotify] Messages are currently being shown for OPs.");
              sender.sendMessage(ChatColor.LIGHT_PURPLE + "[SleepNotify] To change, type /sn showop [enable/disable]");
            }
            else
            {
              sender.sendMessage(ChatColor.LIGHT_PURPLE + "[SleepNotify] Messages are currently being hidden for OPs.");
              sender.sendMessage(ChatColor.LIGHT_PURPLE + "[SleepNotify] To change, type /sn showop [enable/disable]");
            }
          }
          else if (args.length == 2) {
            if ((args[1].equalsIgnoreCase("enable")) || (args[1].equalsIgnoreCase("true")))
            {
              if (this.plugin.getConfig().getBoolean("Show Alerts for OPs"))
              {
                sender.sendMessage(ChatColor.RED + "[SleepNotify] Messages are already being shown for OPs!");
              }
              else
              {
                this.plugin.getConfig().set("Show Alerts for OPs", true);
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "[SleepNotify] Messages will now be shown for OPs.");
              }
            }
            else if ((args[1].equalsIgnoreCase("disable")) || (args[1].equalsIgnoreCase("false"))) {
              if (!this.plugin.getConfig().getBoolean("Show Alerts for OPs"))
              {
                sender.sendMessage(ChatColor.RED + "[SleepNotify] Messages are already hidden for OPs!");
              }
              else
              {
                this.plugin.getConfig().set("Show Alerts for OPs", false);
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "[SleepNotify] Messages will now be hidden for OPs.");
              }
            }
          }
        }
        else {
          sender.sendMessage(ChatColor.RED + "You don't have permission to perform that command!");
        }
        return true;
      }
      if (args[0].equalsIgnoreCase("autokick"))
      {
        if ((sender.hasPermission("sleepnotify.admin")) || (sender.isOp()))
        {
          if (args.length == 2)
          {
            if ((args[1].equalsIgnoreCase("enable")) || (args[1].equalsIgnoreCase("true")))
            {
              if (!kickPlayers)
              {
                this.plugin.getConfig().set("Kick Awake Players If At Least Half of Players Are Asleep", true);
                sender.sendMessage(ChatColor.GREEN + "[SleepNotify] Auto-Kicking has been enabled!");
                return true;
              }
              sender.sendMessage(ChatColor.RED + "[SleepNotify] Auto-Kicking is already enabled!");
              return true;
            }
            if ((args[1].equalsIgnoreCase("disable")) || (args[1].equalsIgnoreCase("false")))
            {
              if (kickPlayers)
              {
                this.plugin.getConfig().set("Kick Awake Players If At Least Half of Players Are Asleep", false);
                sender.sendMessage(ChatColor.GREEN + "[SleepNotify] Auto-Kicking has been disabled!");
                return true;
              }
              sender.sendMessage(ChatColor.RED + "[SleepNotify] Auto-Kicking is already disabled!");
              return true;
            }
          }
          if (args.length == 1) {
            if (kickPlayers)
            {
              sender.sendMessage(ChatColor.LIGHT_PURPLE + "[SleepNotify] Auto-Kicking is currently enabled.");
              sender.sendMessage(ChatColor.LIGHT_PURPLE + "[SleepNotify] To change, type /sn autokick [enable/disable]");
            }
            else
            {
              sender.sendMessage(ChatColor.LIGHT_PURPLE + "[SleepNotify] Auto-Kicking is currently disabled.");
              sender.sendMessage(ChatColor.LIGHT_PURPLE + "[SleepNotify] To change, type /sn autokick [enable/disable]");
            }
          }
        }
        else
        {
          sender.sendMessage(ChatColor.RED + "You don't have permission to use that command!");
          return true;
        }
        return true;
      }
      if (args[0].equalsIgnoreCase("broadcastmsg"))
      {
        if ((sender.hasPermission("sleepnotify.admin")) || (sender.isOp()))
        {
          if (args.length == 2)
          {
            if ((args[1].equalsIgnoreCase("enable")) || (args[1].equalsIgnoreCase("true")))
            {
              if (!broadcastMsg)
              {
                this.plugin.getConfig().set("Broadcast Messages", true);
                sender.sendMessage(ChatColor.GREEN + "[SleepNotify] Message broadcasting has been enabled!");
                return true;
              }
              sender.sendMessage(ChatColor.RED + "[SleepNotify] Message broadcasting is already enabled!");
              return true;
            }
            if ((args[1].equalsIgnoreCase("disable")) || (args[1].equalsIgnoreCase("false")))
            {
              if (broadcastMsg)
              {
                this.plugin.getConfig().set("Broadcast Messages", false);
                sender.sendMessage(ChatColor.GREEN + "[SleepNotify] Message broadcasting has been disabled!");
                return true;
              }
              sender.sendMessage(ChatColor.RED + "[SleepNotify] Message broadcasting is already disabled!");
              return true;
            }
          }
          if (args.length == 1) {
            if (broadcastMsg)
            {
              sender.sendMessage(ChatColor.LIGHT_PURPLE + "[SleepNotify] Message broadcasting is currently enabled.");
              sender.sendMessage(ChatColor.LIGHT_PURPLE + "[SleepNotify] To change, type /sn broadcastmsg [enable/disable]");
            }
            else
            {
              sender.sendMessage(ChatColor.LIGHT_PURPLE + "[SleepNotify] Message broadcasting is currently disabled.");
              sender.sendMessage(ChatColor.LIGHT_PURPLE + "[SleepNotify] To change, type /sn broadcastmsg [enable/disable]");
            }
          }
        }
        else
        {
          sender.sendMessage(ChatColor.RED + "You don't have permission to use that command!");
          return true;
        }
        return true;
      }
      if (args[0].equalsIgnoreCase("about"))
      {
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "SleepNotify v" + this.plugin.getDescription().getVersion().toString());
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "SleepNotify is developed by iTechRemix.");
        sender.sendMessage(ChatColor.LIGHT_PURPLE + "http://dev.bukkit.org/bukkit-mods/sleepnotify");
        return true;
      }
    }
    return false;
  }
  
  public boolean containsIgnoreCase(String[] array, String s)
  {
    for (String i : array) {
      if (s.equalsIgnoreCase(i)) {
        return true;
      }
    }
    return false;
  }
}

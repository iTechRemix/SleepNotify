package com.itechremix.plugins.bukkit.sleepnotify;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SleepNotifyListener implements Listener
{
  SleepNotify plugin;
  public static ArrayList<String> sleeping = new ArrayList();
  public static ArrayList<String> awake = new ArrayList();
  public static ArrayList<Player> sleepingObjects = new ArrayList();
  public static ArrayList<Player> awakeObjects = new ArrayList();
  String hidePerm = "sleepnotify.hide";
  String wakePerm = "sleepnotify.wake";
  String sleepPerm = "sleepnotify.sleep";
  
  public SleepNotifyListener(SleepNotify instance)
  {
    this.plugin = instance;
  }
  
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event)
  {
    Player p = event.getPlayer();
    
    FileConfiguration config = this.plugin.getConfig();
    
    String pName = p.getName();
    String pNick = p.getDisplayName();
    
    boolean useNicks = config.getBoolean("Use Nicknames");
    if (!awakeObjects.contains(p)) {
      awakeObjects.add(p);
    }
    if (useNicks)
    {
      if (!awake.contains(pNick)) {
        awake.add(pNick);
      }
    }
    else if ((!useNicks) && (!awake.contains(pName))) {
      awake.add(pName);
    }
  }
  
  @EventHandler
  public void onPlayerSleep(PlayerBedEnterEvent event)
  {
    Player p = event.getPlayer();
    
    String pName = p.getName();
    String pNick = p.getDisplayName();
    
    FileConfiguration config = this.plugin.getConfig();
    
    String sleepMsg = config.getString("Sleep Message");
    ChatColor nameColor = ChatColor.valueOf(config.getString("Player Name Color"));
    ChatColor msgColor = ChatColor.valueOf(config.getString("Message Color"));
    boolean useNicks = config.getBoolean("Use Nicknames");
    boolean showOp = config.getBoolean("Show Alerts for OPs");
    boolean kickPlayers = config.getBoolean("Kick Awake Players If At Least Half of Players Are Asleep");
    boolean broadcastMsg = config.getBoolean("Broadcast Messages");
    if (!sleepingObjects.contains(p)) {
      sleepingObjects.add(p);
    }
    if (awakeObjects.contains(p)) {
      awakeObjects.remove(p);
    }
    if (p.isOp())
    {
      if ((showOp) && (!p.hasPermission(this.hidePerm))) {
        if (useNicks)
        {
          awake.remove(pNick);
          sleeping.add(pNick);
          if (broadcastMsg) {
            Bukkit.getServer().broadcastMessage(nameColor + pNick + " " + msgColor + sleepMsg);
          }
        }
        else
        {
          awake.remove(pName);
          sleeping.add(pName);
          if (broadcastMsg) {
            Bukkit.getServer().broadcastMessage(nameColor + pName + " " + msgColor + sleepMsg);
          }
        }
      }
    }
    else if ((!p.isOp()) && (!p.hasPermission(this.hidePerm))) {
      if (useNicks)
      {
        awake.remove(pNick);
        sleeping.add(pNick);
        if (broadcastMsg) {
          Bukkit.getServer().broadcastMessage(nameColor + pNick + " " + msgColor + sleepMsg);
        }
      }
      else
      {
        awake.remove(pName);
        sleeping.add(pName);
        if (broadcastMsg) {
          Bukkit.getServer().broadcastMessage(nameColor + pName + " " + msgColor + sleepMsg);
        }
      }
    }
    if ((kickPlayers) && (sleepingObjects.size() <= Bukkit.getServer().getOnlinePlayers().size() / 2)) {
      for (Player pl : awakeObjects) {
        if ((!pl.isOp()) && (!pl.hasPermission("sleepnotify.nokick"))) {
          pl.kickPlayer("You were awake when most were asleep!");
        }
      }
    }
  }
  
  @EventHandler
  public void onPlayerWake(PlayerBedLeaveEvent event)
  {
    Player p = event.getPlayer();
    
    String pName = p.getName();
    String pNick = p.getDisplayName();
    
    FileConfiguration config = this.plugin.getConfig();
    
    String wakeMsg = config.getString("Wake Message");
    ChatColor nameColor = ChatColor.valueOf(config.getString("Player Name Color"));
    ChatColor msgColor = ChatColor.valueOf(config.getString("Message Color"));
    boolean useNicks = config.getBoolean("Use Nicknames");
    boolean showOp = config.getBoolean("Show Alerts for OPs");
    boolean broadcastMsg = config.getBoolean("Broadcast Messages");
    if (sleepingObjects.contains(p)) {
      sleepingObjects.remove(p);
    }
    if (!awakeObjects.contains(p)) {
      awakeObjects.add(p);
    }
    if (p.isOp())
    {
      if ((showOp) && (!p.hasPermission(this.hidePerm))) {
        if (useNicks)
        {
          if (broadcastMsg) {
            Bukkit.getServer().broadcastMessage(nameColor + pNick + " " + msgColor + wakeMsg);
          }
        }
        else if (broadcastMsg) {
          Bukkit.getServer().broadcastMessage(nameColor + pName + " " + msgColor + wakeMsg);
        }
      }
    }
    else if ((!p.isOp()) && (!p.hasPermission(this.hidePerm))) {
      if (useNicks)
      {
        if (broadcastMsg) {
          Bukkit.getServer().broadcastMessage(nameColor + pNick + " " + msgColor + wakeMsg);
        }
      }
      else if (broadcastMsg) {
        Bukkit.getServer().broadcastMessage(nameColor + pName + " " + msgColor + wakeMsg);
      }
    }
    if (sleeping.contains(pName))
    {
      awake.add(pName);
      sleeping.remove(pName);
    }
    if (sleeping.contains(pNick))
    {
      awake.add(pNick);
      sleeping.remove(pNick);
    }
    if (sleepingObjects.contains(p)) {
      sleepingObjects.remove(p);
    }
    if (!awakeObjects.contains(p)) {
      awakeObjects.add(p);
    }
  }
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event)
  {
    Player p = event.getPlayer();
    String pName = p.getName();
    String pNick = p.getDisplayName();
    if (sleeping.contains(pName)) {
      sleeping.remove(pName);
    }
    if (sleeping.contains(pNick)) {
      sleeping.remove(pNick);
    }
    if (awake.contains(pName)) {
      awake.remove(pName);
    }
    if (awake.contains(pNick)) {
      awake.remove(pNick);
    }
    if (sleepingObjects.contains(p)) {
      sleepingObjects.remove(p);
    }
    if (awakeObjects.contains(p)) {
      awakeObjects.remove(p);
    }
  }
}

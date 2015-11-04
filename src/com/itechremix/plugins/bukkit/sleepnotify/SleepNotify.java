package com.itechremix.plugins.bukkit.sleepnotify;

import org.bukkit.plugin.java.JavaPlugin;

public class SleepNotify extends JavaPlugin
{
  public void onEnable()
  {
    SleepNotifyConfig configClass = new SleepNotifyConfig(this);
    getServer().getPluginManager().registerEvents(new SleepNotifyListener(this), this);
    getCommand("sleeping").setExecutor(new SleepNotifyCommands(this));
    getCommand("awake").setExecutor(new SleepNotifyCommands(this));
    getCommand("sn").setExecutor(new SleepNotifyConfigCommands(this));
    getCommand("sleepnotify").setExecutor(new SleepNotifyConfigCommands(this));
    configClass.setupConfig();
    System.out.println("[SleepNotify] SleepNotify " + getDescription().getVersion() + " has been enabled!");
  }
  
  public void onDisable()
  {
    SleepNotifyListener.sleeping.clear();
    SleepNotifyListener.awake.clear();
    SleepNotifyListener.awakeObjects.clear();
    SleepNotifyListener.sleepingObjects.clear();
    saveConfig();
  }
}
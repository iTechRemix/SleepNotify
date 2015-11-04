package com.itechremix.plugins.bukkit.sleepnotify;

import org.bukkit.configuration.file.FileConfiguration;

public class SleepNotifyConfig
{
  SleepNotify plugin;
  FileConfiguration config;
  
  public SleepNotifyConfig(SleepNotify instance)
  {
    this.plugin = instance;
    this.config = this.plugin.getConfig();
  }
  
  public void setupConfig()
  {
    this.config.addDefault("Broadcast Messages", true);
    this.config.addDefault("Wake Message", "has woken up");
    this.config.addDefault("Sleep Message", "has gone to sleep");
    this.config.addDefault("Player Name Color", "AQUA");
    this.config.addDefault("Message Color", "GOLD");
    this.config.addDefault("Use Nicknames", true);
    this.config.addDefault("Show Alerts for OPs", true);
    this.config.addDefault("Kick Awake Players If At Least Half of Players Are Asleep", false);
    this.config.options().copyDefaults(true);
    this.plugin.saveConfig();
  }
}
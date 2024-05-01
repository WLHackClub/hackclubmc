package io.github.wlhackclub.hackclubmc;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {

        this.getCommand("givegun").setExecutor(new GiveGunCommand());
        this.getServer().getPluginManager().registerEvents(new GunListener(), this);

        this.getCommand("givewarpdrive").setExecutor(new WarpDriveCommand());
        this.getServer().getPluginManager().registerEvents(new WarpDriveListener(), this);

        this.getCommand("givehyperion").setExecutor(new HyperionCommand());
        this.getServer().getPluginManager().registerEvents(new HyperionListener(), this);

        new AirDefenseScanner(this).runTaskTimer(this, 200L, 30L);

    }

}
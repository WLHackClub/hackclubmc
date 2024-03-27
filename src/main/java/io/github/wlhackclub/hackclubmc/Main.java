package io.github.wlhackclub.hackclubmc;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {

        this.getCommand("givegun").setExecutor(new GiveGunCommand());
        this.getServer().getPluginManager().registerEvents(new GunListener(), this);

        this.getCommand("givewarpdrive").setExecutor(new WarpDriveCommand());

    }

}
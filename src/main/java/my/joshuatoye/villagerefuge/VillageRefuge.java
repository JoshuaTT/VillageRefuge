package my.joshuatoye.villagerefuge;

import org.bukkit.plugin.java.JavaPlugin;

public final class VillageRefuge extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new BellListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

package com.github.evancolewright.autobroadcaster;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class AutoBroadcaster extends JavaPlugin
{
    private final Logger log = getLogger();
    private boolean hasPlaceholderAPI = false;

    @Override
    public void onEnable()
    {
        saveDefaultConfig();
        checkPlaceholderAPI();
        getServer().getScheduler().scheduleSyncRepeatingTask(
                this,
                new AutoBroadcasterRunnable(this),
                20,
                20 * 60 * getConfig().getInt("broadcast_interval"));
        log.info("Setup complete! For support, please PM me on SpigotMC or open an issue on GitHub.");
    }

    private void checkPlaceholderAPI()
    {
        Plugin placeholderAPI = getServer().getPluginManager().getPlugin("PlaceholderAPI");
        if (placeholderAPI == null)
        {
            log.warning("Could not find PlaceholderAPI, therefore you will NOT be able to use placeholders in your broadcast messages!  You can download it from here: https://www.spigotmc.org/resources/placeholderapi.6245/");
        } else
        {
            log.info("Successfully hooked into PlaceholderAPI!");
            hasPlaceholderAPI = true;
        }
    }

    protected boolean hasPlaceholderAPI()
    {
        return hasPlaceholderAPI;
    }
}

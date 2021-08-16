package com.github.evancolewright.autobroadcaster;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AutoBroadcasterRunnable implements Runnable
{
    private final AutoBroadcaster plugin;
    private final FileConfiguration configuration;
    private final List<List<String>> broadcastMessages = new ArrayList<>();

    private boolean soundEnabled;
//    private Sound sound;

    int currentPos = 0;
    int maxPos;

    protected AutoBroadcasterRunnable(AutoBroadcaster plugin)
    {
        this.plugin = plugin;
        this.configuration = plugin.getConfig();

        loadBroadcastMessages();
        loadSoundSettings();

        this.maxPos = this.broadcastMessages.size() - 1;
    }

    @Override
    public void run()
    {
        //            if (soundEnabled) player.playSound(player.getLocation(), sound, 2f, 2f);
        for (Player player : Bukkit.getOnlinePlayers())
            formatList(broadcastMessages.get(currentPos), player)
                    .forEach(player::sendMessage);
        if (++currentPos == maxPos + 1) currentPos = 0;
    }

    private List<String> formatList(List<String> listMessage, Player player)
    {
        return listMessage
                .stream()
                .map(string ->
                {
                    return ChatColor.translateAlternateColorCodes('&',
                            plugin.hasPlaceholderAPI() ?  PlaceholderAPI.setPlaceholders(player, string) : string);
                }).collect(Collectors.toList());
    }

    private void loadBroadcastMessages()
    {
        for (String s : configuration.getConfigurationSection("messages").getKeys(false))
        {
            this.broadcastMessages.add(configuration.getStringList("messages." + s));
        }
    }

    private void loadSoundSettings()
    {
//        soundEnabled = configuration.getBoolean("sound.enabled");
//        sound = Sound.valueOf(configuration.getString("sound.value"));
    }

}

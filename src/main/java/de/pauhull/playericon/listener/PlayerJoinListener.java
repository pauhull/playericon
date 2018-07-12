package de.pauhull.playericon.listener;

import de.pauhull.playericon.PlayerIcon;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    private PlayerIcon playerIcon;

    public PlayerJoinListener(PlayerIcon playerIcon) {
        this.playerIcon = playerIcon;

        Bukkit.getPluginManager().registerEvents(this, playerIcon.getPlugin());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        playerIcon.getIpManager().registerIP(event.getPlayer());
    }

}

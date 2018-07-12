package de.pauhull.playericon.listener;

import de.pauhull.playericon.PlayerIcon;
import de.pauhull.playericon.util.IconPlayer;
import de.pauhull.playericon.util.ImageRenderer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import java.awt.image.BufferedImage;
import java.net.InetAddress;

public class ServerListPingListener implements Listener {

    private PlayerIcon playerIcon;

    public ServerListPingListener(PlayerIcon playerIcon) {
        this.playerIcon = playerIcon;

        Bukkit.getPluginManager().registerEvents(this, playerIcon.getPlugin());
    }

    @EventHandler
    public void onServerListPing(ServerListPingEvent event) throws Exception {
        if (!playerIcon.isEnabled())
            return;

        InetAddress address = event.getAddress();
        IconPlayer player = playerIcon.getIpManager().getIconPlayerFromIP(address);

        if (player == null)
            player = playerIcon.getNotJoinedYet();

        BufferedImage iconImage = ImageRenderer.createIcon(player, playerIcon.getBackground(), playerIcon.getOverlay(),
                playerIcon.is3D(), playerIcon.getScaleFactor());

        event.setServerIcon(Bukkit.loadServerIcon(iconImage));
    }

}

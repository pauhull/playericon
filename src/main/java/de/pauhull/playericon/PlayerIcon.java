package de.pauhull.playericon;

import de.pauhull.playericon.command.PlayerIconCommand;
import de.pauhull.playericon.config.Configuration;
import de.pauhull.playericon.listener.PlayerJoinListener;
import de.pauhull.playericon.listener.ServerListPingListener;
import de.pauhull.playericon.util.IPManager;
import de.pauhull.playericon.util.IconPlayer;
import de.pauhull.playericon.util.UUIDFetcher;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

public class PlayerIcon {

    @Getter
    private static PlayerIcon instance;

    @Getter
    private JavaPlugin plugin;

    @Getter
    private ScheduledExecutorService scheduledExecutorService;

    @Getter
    private ExecutorService executorService;

    @Getter
    private Configuration configuration;

    @Getter
    private IPManager ipManager;

    @Getter
    private boolean enabled, is3D;

    @Getter
    private IconPlayer notJoinedYet;

    @Getter
    private BufferedImage background = null, overlay = null;

    @Getter
    private double scaleFactor;

    public PlayerIcon(JavaPlugin plugin, ScheduledExecutorService scheduledExecutorService, ExecutorService executorService, Configuration configuration) {
        instance = this;

        this.plugin = plugin;
        this.scheduledExecutorService = scheduledExecutorService;
        this.executorService = executorService;
        this.configuration = configuration;
    }

    public void start() throws Exception {

        //Init Events
        new PlayerJoinListener(this);
        new ServerListPingListener(this);

        //Init Commands
        new PlayerIconCommand(this);

        //Init IPManager
        ipManager = new IPManager(this);
        ipManager.loadIPs();

        this.reloadConfig();

    }

    public void stop() throws IOException {
        ipManager.saveIPs();
    }

    public void reloadConfig() throws Exception {
        scaleFactor = configuration.getConfig().getDouble("ScaleFactor");
        enabled = configuration.getConfig().getBoolean("Enabled");
        is3D = configuration.getConfig().getBoolean("Display3D");
        String notJoinedYetName = configuration.getConfig().getString("NotJoinedYet");
        UUID notJoinedYetUUID = UUIDFetcher.fetchUUID(notJoinedYetName);
        notJoinedYet = new IconPlayer(notJoinedYetName, notJoinedYetUUID, null);
        File overlayFile = new File("plugins/PlayerIcon/" + configuration.getConfig().getString("OverlayFile"));
        File backgroundFile = new File("plugins/PlayerIcon/" + configuration.getConfig().getString("BackgroundFile"));
        if (overlayFile.exists()) overlay = ImageIO.read(overlayFile);
        if (backgroundFile.exists()) background = ImageIO.read(backgroundFile);
    }

}

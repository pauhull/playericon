package de.pauhull.playericon;

import de.pauhull.playericon.config.Configuration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Main extends JavaPlugin {

    public static final String PLUGIN_NAME = "PlayerIcon";

    private int threadID;
    private PlayerIcon playerIcon;
    private Configuration configuration;

    @Override
    public void onEnable() {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(this::createThread);
        ExecutorService executorService = Executors.newCachedThreadPool(this::createThread);

        configuration = new Configuration(new File("plugins/PlayerIcon/config.yml"));

        playerIcon = new PlayerIcon(this, scheduledExecutorService, executorService, configuration);
        try {
            playerIcon.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        try {
            playerIcon.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Thread createThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.setName(PLUGIN_NAME + " Task # " + (++threadID));
        return thread;
    }

}

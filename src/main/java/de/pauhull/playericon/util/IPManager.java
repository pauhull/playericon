package de.pauhull.playericon.util;

import de.pauhull.playericon.PlayerIcon;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IPManager {

    private PlayerIcon playerIcon;
    private File ipsFile;
    private FileConfiguration config;
    private List<IconPlayer> players;

    public IPManager(PlayerIcon playerIcon) throws IOException {
        this.playerIcon = playerIcon;
        this.players = new ArrayList<>();
        this.ipsFile = new File("plugins/PlayerIcon/ips.yml");
        this.ipsFile.getParentFile().mkdirs();
        this.ipsFile.createNewFile();

        if (this.ipsFile.exists())
            config = YamlConfiguration.loadConfiguration(this.ipsFile);

    }

    public void loadIPs() throws UnknownHostException {
        if (!ipsFile.exists())
            return;

        for (String key : config.getKeys(false)) {
            UUID uuid = UUID.fromString(key);
            String name = config.getString(key + ".Name");
            InetAddress address = InetAddress.getByName(config.getString(key + ".Address"));
            players.add(new IconPlayer(name, uuid, address));
        }
    }

    public void saveIPs() throws IOException {
        for (IconPlayer player : players) {
            String uuid = player.getUuid().toString();
            config.set(uuid + ".Name", player.getName());
            config.set(uuid + ".Address", player.getAddress().getHostName());
        }

        config.save(this.ipsFile);
    }

    public IconPlayer getIconPlayerFromIP(InetAddress address) {
        for (IconPlayer player : players) {
            if (address.equals(player.getAddress())) {
                return player;
            }
        }

        return null;
    }

    public void registerIP(Player player) {
        InetAddress address = player.getAddress().getAddress();
        UUID uuid = player.getUniqueId();
        String name = player.getName();
        players.add(new IconPlayer(name, uuid, address));
    }

}

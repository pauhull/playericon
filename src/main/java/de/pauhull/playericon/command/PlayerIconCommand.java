package de.pauhull.playericon.command;

import de.pauhull.playericon.Main;
import de.pauhull.playericon.PlayerIcon;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PlayerIconCommand implements CommandExecutor {

    private PlayerIcon playerIcon;

    public PlayerIconCommand(PlayerIcon playerIcon) {
        this.playerIcon = playerIcon;

        playerIcon.getPlugin().getCommand("playericon").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("playericon")) {
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {

                    if (sender.hasPermission("playericon.reload")) {
                        try {
                            playerIcon.reloadConfig();
                            sender.sendMessage("§aConfig reloaded successfully!");
                        } catch (Exception e) {
                            sender.sendMessage("§cAn error occurred while reloading the config.");
                            e.printStackTrace();
                        }
                        return true;
                    }

                    sender.sendMessage("§cYou do not have enough permissions to execute this command!");
                    return true;
                }
            }

            sender.sendMessage("[" + Main.PLUGIN_NAME + "] Version " + playerIcon.getPlugin().getDescription().getVersion() + " by pauhull");
        }
        return true;
    }

}

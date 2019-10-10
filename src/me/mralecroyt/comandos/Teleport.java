package me.mralecroyt.comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Teleport implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String string, String[] args) {
        if (cmd.getName().equalsIgnoreCase("tp")) {
            Player p = (Player)sender;
            if ((sender instanceof Player)) {
                if (!p.hasPermission("groy.tp")) {
                    return false;
                }
                if (args.length == 0) {
                    p.sendMessage("§a§lSM §8» §fUsa: §a/tp (jugador)§f.");
                    return true;
                }
                if (args.length != 1) {
                    return false;
                }
                try {
                    Player obj = Bukkit.getPlayer(args[0]);
                    if (obj.isOnline()) {
                        p.sendMessage("§a§lSM §8» §fTeletransportando...");
                        p.teleport(obj.getLocation());
                        return true;
                    }
                    p.sendMessage("§a§lSM §8» §fEl Jugador no esta conectado.");
                    return true;
                } catch (Exception e) {
                }
            }
            sender.sendMessage("Debes ser un jugador");
            return true;
        }
        return false;
    }
}


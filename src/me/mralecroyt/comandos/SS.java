package me.mralecroyt.comandos;

import me.mralecroyt.Listener.Utils.Util;
import me.mralecroyt.LobbyCore.CoreMain;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.block.CommandBlock;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class SS implements CommandExecutor {
    public SS() {
        super();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("ss")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (!p.hasPermission("groy.ss")) {
                    p.sendMessage("§cComando desconocido.");
                    return false;
                }
                if (args.length == 0) {
                    p.sendMessage("§a§lSM §8» §fUsa: §a/ss (Nombre) (Mensaje)§f.");
                    return true;
                }
                if (args.length >= 2) {
                    if (Bukkit.getPlayer(args[0]) != null) {
                        Player p2 = Bukkit.getPlayer(args[0]);
                        StringBuilder sb = new StringBuilder();
                        for (int i = 1; i < args.length; ++i) {
                            sb.append(" ").append(args[i]);
                        }
                        String message = sb.toString().substring(1);
                        message = ChatColor.translateAlternateColorCodes('&', message);
                        p.sendMessage("§a§lSM §8» §fMensaje de SS enviado a ese jugador.");
                        p2.sendMessage(ChatColor.translateAlternateColorCodes('&', CoreMain.getInstance().getConfig().getString("cmd2.msg.format.toMe").replace("%displayname%", p.getDisplayName()).replace("%message%", message).replace("%name%", p.getName())));
                    }
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', CoreMain.getInstance().getConfig().getString("player.notOnline").replace("%player%", args[0])));
                }
            }
        }
        return true;
    }
}

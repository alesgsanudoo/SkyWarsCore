package me.mralecroyt.comandos;

import me.mralecroyt.LobbyCore.CoreMain;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import org.bukkit.command.CommandExecutor;

public class BC implements CommandExecutor
{
    private ArrayList<Player> use;

    public BC() {
        this.use = new ArrayList<>();
        this.use = new ArrayList<>();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        String command = cmd.getName();
        if (command.equalsIgnoreCase("broadcast")) {
            Player player = (Player)sender;
            if (!player.hasPermission("groy.broadcast") && !player.isOp()) {
                player.sendMessage(ChatColor.RED + "Comando desconocido.");
                return true;
            }
            if (args.length == 0) {
                player.sendMessage("§a§lComandos Broadcast:");
                player.sendMessage("");
                player.sendMessage("§2● §a/broadcast (Mensaje): §fEnviar mensajes global a los jugadores.");
                player.sendMessage("");
                player.sendMessage("§a§lPlugin by GroyLandTeam");
                return true;
            }
            if (args.length >= 1) {
                if (this.use.contains(player)) {
                    player.sendMessage(ChatColor.RED + "§a§lBC §8» §fEspera " + ChatColor.GOLD + "§a20 segundos§f, " + ChatColor.RED + "§fpara volver a ejecutar esto");
                    return true;
                }
                String msg = args[0];
                for (int i = 1; i < args.length; ++i) {
                    msg = msg + " " + args[i];
                }
                Bukkit.broadcastMessage(tk("&a&lBC §8» " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', msg)));
                if (!player.hasPermission("groy.broadcast.bypass")) {
                    this.use.add(player);
                    CoreMain.get().getServer().getScheduler().scheduleSyncDelayedTask(CoreMain.get(), new Runnable() {
                        @Override
                        public void run() {
                            BC.this.use.remove(player);
                        }
                    }, 400L);
                    return true;
                }
                return true;
            }
        }
        return false;
    }

    public static String tk(String a) {
        return a.replaceAll("&", "§");
    }
}
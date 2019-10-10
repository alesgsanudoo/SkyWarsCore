package me.mralecroyt.comandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VIPFLY implements CommandExecutor {

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        final String perms = "§c§l¡ERROR! §fNo tienes estos privilegios, compra tu rango en §atienda.groyland.net§f.";
        if (cmd.getLabel().equalsIgnoreCase("fly")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("" + "§cDebes ser un jugador!");
            }
            final Player p = (Player) sender;
                if (!p.hasPermission("settings.vuelo")) {
                    p.sendMessage(perms);
                    return true;
                }
                if (p.getLocation().getWorld().getName().equals("Lobby")) {
                    if (p.isFlying() || p.getAllowFlight()) {
                        p.sendMessage("§a§lOpciones §8» §cEl modo vuelo  ha sido desaactivado.");
                        p.setAllowFlight(false);
                        p.setFlying(false);
                    } else {
                        p.setAllowFlight(true);
                        p.sendMessage("§a§lOpciones §8» §aEl modo vuelo ha sido activado.");
                        p.setFlying(true);
                }
            }
        }
        return false;
    }
}

package me.mralecroyt.comandos;


import me.mralecroyt.LobbyCore.CoreMain;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.*;

public class Vanish implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        Player p = (Player)sender;
        if (cmd.getName().equalsIgnoreCase("vanish")) {
            if ((!p.hasPermission("groy.vanish")) && (!p.isOp())) {
                p.sendMessage("§cComando desconocido.");
                return true;
            }
            if (args.length == 0)
            {
                if (!CoreMain.vanish.contains(p))
                {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        if (!all.hasPermission("groy.vanish.bypass")) {
                            all.hidePlayer(p);
                        }
                    }
                    CoreMain.vanish.add(p);
                    p.setAllowFlight(true);
                    p.setFlying(true);
                    p.sendMessage("§a§lSM §8» §aHas activado el modo oculto.");
                    return true;
                }
                if (CoreMain.vanish.contains(p))
                {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        all.showPlayer(p);
                    }
                    CoreMain.vanish.remove(p);
                    p.setAllowFlight(false);
                    p.setFlying(false);
                    p.sendMessage("§a§lSM §8» §cHas desactivado el modo oculto.");
                    return true;
                }
            }
            else
            {
                if (args.length != 1)
                {
                    p.sendMessage("§a§lSM §8» §aUsa: /vanish (on/off)§f.");
                    return true;
                }
                if (args[0].equalsIgnoreCase("on"))
                {
                    if (CoreMain.vanish.contains(p))
                    {
                        p.sendMessage("§a§lSM §8» §aYa tienes activado el modo oculto activado.");
                        return true;
                    }
                    if (!CoreMain.vanish.contains(p))
                    {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            if (!all.hasPermission("groy.vanish.bypass")) {
                                all.hidePlayer(p);
                            }
                        }
                        CoreMain.vanish.add(p);
                        p.sendMessage("§a§lSM §8» §aHas activado el modo oculto.");
                        return true;
                    }
                }
                else if (args[0].equalsIgnoreCase("off"))
                {
                    if (!CoreMain.vanish.contains(p))
                    {
                        p.sendMessage("§a§lSM §8» §aYa tienes desactivado el modo oculto activado.");
                        return true;
                    }
                    if (CoreMain.vanish.contains(p))
                    {
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.showPlayer(p);
                        }
                        CoreMain.vanish.remove(p);
                        p.sendMessage("§a§lSM §8» §cHas desactivado el modo oculto.");
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

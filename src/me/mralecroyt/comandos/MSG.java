package me.mralecroyt.comandos;

import me.mralecroyt.Listener.MessengerUser;
import me.mralecroyt.Listener.Utils.Util;
import me.mralecroyt.LobbyCore.CoreMain;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.block.CommandBlock;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;

public class MSG implements CommandExecutor
{
    public MSG() {
        super();
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("msg")) {
            if (sender instanceof Player) {
                Player p = (Player)sender;
                if (args.length == 0) {
                    p.sendMessage("§a§lComandos Mensajes:");
                    p.sendMessage("");
                    p.sendMessage("§2● §a/msg (Jugador) (Mensaje): §fEnviar mensajes a un jugador.");
                    p.sendMessage("§2● §a/msg disable: §fDesactivar mensajes.");
                    p.sendMessage("§2● §a/reply: §fResponder a un jugador. ");
                    p.sendMessage("");
                    p.sendMessage("§a§lPlugin by GroyLandTeam");
                    return true;
                }
                if (args.length >= 2) {
                    if (Bukkit.getPlayer(args[0]) != null) {
                        Player p2 = Bukkit.getPlayer(args[0]);
                        if (CoreMain.getInstance().maySendMessage(p, p2)) {
                            StringBuilder sb = new StringBuilder();
                            for (int i = 1; i < args.length; ++i) {
                                sb.append(" ").append(args[i]);
                            }
                            String message = sb.toString().substring(1);
                            if (Util.containsLink(message) && !p.hasPermission("groy.sendLinks")) {
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', CoreMain.getInstance().getConfig().getString("cmd.msg.mayNotSendLinks")));
                                return true;
                            }
                            if (p.hasPermission("groy.color")) {
                                message = ChatColor.translateAlternateColorCodes('&', message);
                            }
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', CoreMain.getInstance().getConfig().getString("cmd.msg.format.meTo").replace("%displayname%", p2.getDisplayName()).replace("%message%", message).replace("%name%", p2.getName())));
                            p2.sendMessage(ChatColor.translateAlternateColorCodes('&', CoreMain.getInstance().getConfig().getString("cmd.msg.format.toMe").replace("%displayname%", p.getDisplayName()).replace("%message%", message).replace("%name%", p.getName())));
                            for (MessengerUser spy : CoreMain.USER_STORAGE) {
                                if (spy.getPlayer() != null && spy.getPlayer().isOnline() && spy.isSocialSpyActive() && spy.getPlayer() != p && spy.getPlayer() != p2) {
                                    spy.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', CoreMain.getInstance().getConfig().getString("cmd.socialspy.msg").replace("%player1%", p.getDisplayName()).replace("%player2%", p2.getDisplayName()).replace("%message%", message).replace("%name1%", p.getName()).replace("%name2%", p2.getName())));
                                }
                            }
                            for (CommandSender spy2 : CoreMain.SOCIAL_SPY) {
                                if (spy2 != p && spy2 != p2) {
                                    spy2.sendMessage(ChatColor.translateAlternateColorCodes('&', CoreMain.getInstance().getConfig().getString("cmd.socialspy.msg").replace("%player1%", p.getDisplayName()).replace("%player2%", p2.getDisplayName()).replace("%message%", message).replace("%name1%", p.getName()).replace("%name2%", p2.getName())));
                                }
                            }
                            if (CoreMain.REPLY.containsKey(p2)) {
                                CoreMain.REPLY.remove(p2);
                            }
                            CoreMain.REPLY.put(p2, p);
                        }
                        else {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', CoreMain.getInstance().getConfig().getString("player.blockingMessages").replace("%player%", args[0])));
                        }
                    }
                    else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', CoreMain.getInstance().getConfig().getString("player.notOnline").replace("%player%", args[0])));
                    }
                }
            }
            else if (sender instanceof CommandBlock) {
                CommandBlock block = (CommandBlock)sender;
                if (args.length >= 2 && Bukkit.getPlayer(args[0]) != null) {
                    Player p2 = Bukkit.getPlayer(args[0]);
                    StringBuilder sb = new StringBuilder();
                    for (int i = 1; i < args.length; ++i) {
                        sb.append(" ").append(args[i]);
                    }
                    String message = ChatColor.translateAlternateColorCodes('&', sb.toString().substring(1));
                    p2.sendMessage(ChatColor.translateAlternateColorCodes('&', CoreMain.getInstance().getConfig().getString("cmd.msg.format.toMe").replace("%displayname%", "" + block.getName()).replace("%message%", message)));
                }
            }
            else if (sender instanceof ConsoleCommandSender) {
                ConsoleCommandSender console = (ConsoleCommandSender)sender;
                if (args.length >= 2) {
                    if (Bukkit.getPlayer(args[0]) != null) {
                        Player p2 = Bukkit.getPlayer(args[0]);
                        StringBuilder sb = new StringBuilder();
                        for (int i = 1; i < args.length; ++i) {
                            sb.append(" ").append(args[i]);
                        }
                        String message = ChatColor.translateAlternateColorCodes('&', sb.toString().substring(1));
                        console.sendMessage(ChatColor.translateAlternateColorCodes('&', CoreMain.getInstance().getConfig().getString("cmd.msg.format.meTo").replace("%displayname%", p2.getDisplayName()).replace("%message%", message).replace("%name%", p2.getName())));
                        p2.sendMessage(ChatColor.translateAlternateColorCodes('&', CoreMain.getInstance().getConfig().getString("cmd.msg.format.toMe").replace("%displayname%", "&a&lConsola").replace("%message%", message).replace("%name%", "CONSOLE")));
                    }
                    else {
                        console.sendMessage(ChatColor.translateAlternateColorCodes('&', CoreMain.getInstance().getConfig().getString("player.notOnline").replace("%player%", args[0])));
                    }
                }
            }
            else {
                sender.sendMessage(ChatColor.RED + "§a§lMSG §8» §fUso incorrecto." );
            }
        }
        if (args[0].equalsIgnoreCase("socialspy")) {
            if (sender.hasPermission("groy.socialspy")) {
                if (sender instanceof Player) {
                    MessengerUser u = MessengerUser.getUser((Player)sender);
                    if (u.isSocialSpyActive()) {
                        u.setSocialSpyActive(false);
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', CoreMain.getInstance().getConfig().getString("cmd.socialspy.off")));
                    }
                    else {
                        u.setSocialSpyActive(true);
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', CoreMain.getInstance().getConfig().getString("cmd.socialspy.on")));
                    }
                }
                else if (CoreMain.SOCIAL_SPY.contains(sender)) {
                    CoreMain.SOCIAL_SPY.remove(sender);
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', CoreMain.getInstance().getConfig().getString("cmd.socialspy.off")));
                }
                else {
                    CoreMain.SOCIAL_SPY.add(sender);
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', CoreMain.getInstance().getConfig().getString("cmd.socialspy.on")));
                }
            }
            else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', CoreMain.getInstance().getConfig().getString("player.noPermission")));
            }
        }
        if (cmd.getName().equalsIgnoreCase("reply")) {
            if (sender instanceof Player) {
                Player p = (Player)sender;
                if (p.hasPermission("groy.msg")) {
                    if (CoreMain.REPLY.containsKey(p)) {
                        String p3 = CoreMain.REPLY.get(p).getName();
                        if (Bukkit.getPlayer(p3) != null) {
                            StringBuilder sb = new StringBuilder();
                            for (int i = 0; i < args.length; ++i) {
                                sb.append(" ").append(args[i]);
                            }
                            String message = sb.toString().substring(1);
                            p.performCommand("msg " + p3 + " " + message);
                        }
                        else {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', CoreMain.getInstance().getConfig().getString("player.notOnline").replace("%player%", p3)));
                        }
                    }
                    else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', CoreMain.getInstance().getConfig().getString("cmd.reply.noMessageSent")));
                    }
                }
                else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', CoreMain.getInstance().getConfig().getString("player.noPermission")));
                }
            }
            else {
                sender.sendMessage(ChatColor.RED + "§a§lMSG §8» §fUso incorrecto." );
            }
        }
        if (args[0].equalsIgnoreCase("disable")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission("groy.msg")) {
                    if (CoreMain.BLOCK_MSG.contains(p)) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', CoreMain.getInstance().getConfig().getString("cmd.blockmsg.deactivated")));
                        CoreMain.BLOCK_MSG.remove(p);
                    } else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', CoreMain.getInstance().getConfig().getString("cmd.blockmsg.activated")));
                        CoreMain.BLOCK_MSG.add(p);
                    }
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', CoreMain.getInstance().getConfig().getString("player.noPermission")));
                }
            }
            else {
                sender.sendMessage(ChatColor.RED + "§a§lMSG §8» §fUso incorrecto." );
            }
        }
        if (args[0].equalsIgnoreCase("help")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (p.hasPermission("groy.msg")) {
                    p.sendMessage("§a§lComandos Mensajes:");
                    p.sendMessage("");
                    p.sendMessage("§2● §a/msg (Jugador) (Mensaje): §fEnviar mensajes a un jugador.");
                    p.sendMessage("§2● §a/msg disable: §fDesactivar mensajes.");
                    p.sendMessage("§2● §a/reply: §fResponder a un jugador. ");
                    p.sendMessage("");
                    p.sendMessage("§a§lPlugin by GroyLandTeam");
                }
            }
        }
        return true;
    }
}

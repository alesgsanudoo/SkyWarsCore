package me.mralecroyt.Eventos.ServerOptions;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PerWorldTab implements Listener {
    @EventHandler
    public void onJoin(final PlayerChangedWorldEvent event) {
        final Player player = event.getPlayer();
        final World world = player.getWorld();
            for (final Player players : Bukkit.getServer().getOnlinePlayers()) {
                if (players.getWorld() == world) {
                    players.showPlayer(player);
                    player.showPlayer(players);
                } else {
                    players.hidePlayer(player);
                    player.hidePlayer(players);
                }
            }
        }
    @EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final World world = player.getWorld();
            for (final Player players : Bukkit.getServer().getOnlinePlayers()) {
                if (players.getWorld() == world) {
                    players.showPlayer(player);
                    player.showPlayer(players);
                }
                else {
                    players.hidePlayer(player);
                    player.hidePlayer(players);
                }
            }
        }
    }


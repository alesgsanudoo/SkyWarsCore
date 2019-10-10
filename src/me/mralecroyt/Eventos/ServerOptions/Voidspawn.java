package me.mralecroyt.Eventos.ServerOptions;


import org.bukkit.event.entity.*;
import me.mralecroyt.LobbyCore.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;

public class Voidspawn implements Listener
{
    @EventHandler
    public void onDamage(final EntityDamageEvent e) {
        final Entity entity = e.getEntity();
        if (entity.getLocation().getWorld().getName().equals("Lobby")) {
            if (CoreMain.getInstance().getConfig().getBoolean("VoidSpawn") && e.getEntity() instanceof Player && e.getEntity().getType().equals(EntityType.PLAYER)) {
                if (e.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
                    final Player p = (Player) e.getEntity();
                    WorldSpawn.teleportJoinSpawn(p);
                    e.setDamage(0.0);
                    e.setCancelled(true);
                } else {
                    e.setCancelled(true);
                }
            }
        }
    }

}

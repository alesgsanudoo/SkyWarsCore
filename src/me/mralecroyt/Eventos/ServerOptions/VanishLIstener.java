package me.mralecroyt.Eventos.ServerOptions;

import me.mralecroyt.LobbyCore.CoreMain;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class VanishLIstener implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        CoreMain.vanish.remove(p);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerBlockInteract(final PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (CoreMain.vanish.contains(p)) {
            e.setCancelled(true);
            Inventory inv = null;
            boolean b = false;
            if (e.getAction() != Action.RIGHT_CLICK_BLOCK && e.getAction() != Action.LEFT_CLICK_BLOCK) {
                return;
            }
            if (e.getClickedBlock().getType() == Material.CHEST) {
                final Chest chest = (Chest) e.getClickedBlock().getState();
                inv = Bukkit.createInventory(null, chest.getInventory().getSize(), "§8Visualizando contenido...");
                inv.setContents(chest.getInventory().getContents());
                b = true;
            } else if (e.getClickedBlock().getType() == Material.ENDER_CHEST) {
                inv = Bukkit.createInventory(null, InventoryType.CHEST, "§8Visualizando contenido...");
                inv.setContents(e.getPlayer().getEnderChest().getContents());
                b = true;
            } else if (e.getClickedBlock().getType() == Material.TRAPPED_CHEST) {
                inv = Bukkit.createInventory(null, InventoryType.CHEST, "§8Visualizando contenido...");
                inv.setContents(e.getPlayer().getEnderChest().getContents());
                b = true;
            } else if (e.getClickedBlock().getType() == Material.HOPPER) {
                final Hopper hopper = (Hopper) e.getClickedBlock().getState();
                inv = Bukkit.createInventory(null, InventoryType.HOPPER, "§8Visualizando contenido...");
                inv.setContents(hopper.getInventory().getContents());
                b = true;
            } else if (e.getClickedBlock().getType() == Material.DISPENSER) {
                final Dropper dropper = (Dropper) e.getClickedBlock().getState();
                inv = Bukkit.createInventory(null, InventoryType.DISPENSER, "§8Visualizando contenido...");
                inv.setContents(dropper.getInventory().getContents());
                b = true;
            } else if (e.getClickedBlock().getType() == Material.FURNACE) {
                final Furnace dispenser = (Furnace) e.getClickedBlock().getState();
                inv = Bukkit.createInventory(null, InventoryType.FURNACE, "§8Visualizando contenido...");
                inv.setContents(dispenser.getInventory().getContents());
                b = true;
            } else if (e.getClickedBlock().getType() == Material.BREWING_STAND) {
                final BrewingStand disp = (BrewingStand) e.getClickedBlock().getState();
                inv = Bukkit.createInventory(null, InventoryType.BREWING, "§8Visualizando contenido...");
                inv.setContents(disp.getInventory().getContents());
                b = true;
            }
            if (b && inv != null) {
                p.openInventory(inv);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFakeChest(final InventoryClickEvent e) {
        if (e.getInventory() != null && e.getInventory().getTitle().equals("§8Visualizando contenido...")) {
            e.setCancelled(true);
        }
        if (e.getWhoClicked().getOpenInventory() != null && e.getWhoClicked().getOpenInventory().getTopInventory() != null && e.getWhoClicked().getOpenInventory().getTopInventory().getTitle().equals("§8Visualizando contenido...")) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerItemPickup(final PlayerPickupItemEvent e) {
        Player p = e.getPlayer();
        if (CoreMain.vanish.contains(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onFixGlitch(final PlayerJoinEvent e) {
        final Player p = e.getPlayer();
        for (final Player v : Bukkit.getOnlinePlayers()) {
            if (CoreMain.vanish.contains(v)) {
                if (p.hasPermission("groy.vanish.bypass")) {
                    p.showPlayer(v);
                }
                else {
                    p.hidePlayer(v);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onFixWorldGlitch(final PlayerChangedWorldEvent e) {
        final Player p = e.getPlayer();
        for (Player v : Bukkit.getOnlinePlayers()) {
            if (!p.hasPermission("latin.vanish.bypass") && CoreMain.vanish.contains(v)) {
                p.hidePlayer(v);
            }
        }
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerItemDrop(final PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if (CoreMain.vanish.contains(p)) {
            e.setCancelled(true);
        }
    }
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerBuild(final BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (CoreMain.vanish.contains(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerBreak(final BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (CoreMain.vanish.contains(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerBreak(final PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();
        if (CoreMain.vanish.contains(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerBreak(final FoodLevelChangeEvent e) {
        Player p = (Player) e.getEntity();
        if (CoreMain.vanish.contains(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerBreak(final PlayerFishEvent e) {
        Player p = e.getPlayer();
        if (CoreMain.vanish.contains(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onTargetEntity(final EntityTargetEvent e) {
        Player p = (Player) e.getTarget();
        if (e.getTarget() instanceof Player && CoreMain.vanish.contains(p)) {
            e.setCancelled(true);
            e.getTarget().getLocation();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onVehicleCrash(final VehicleEntityCollisionEvent e) {
        Player p = (Player) e.getEntity();
        if (e.getEntity() instanceof Player && CoreMain.vanish.contains(p)) {
            e.setCollisionCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerEat(final PlayerItemConsumeEvent e) {
        Player p = e.getPlayer();
        if (CoreMain.vanish.contains(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDamage(final EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        final Player p = (Player)e.getEntity();
            if (CoreMain.vanish.contains(p)) {
                e.setCancelled(true);
            }
            if (e.getEntity().getFireTicks() > 0) {
                e.getEntity().setFireTicks(0);
            }
        }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerDamgeEntity(final EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        final Player p = (Player)e.getEntity();
        if (CoreMain.vanish.contains(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerBedEnter(final PlayerBedEnterEvent e) {
        Player p = e.getPlayer();
        if (CoreMain.vanish.contains(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onFlyChange(final PlayerToggleFlightEvent e) {
        Player p = e.getPlayer();
        if (CoreMain.vanish.contains(p)) {
            e.setCancelled(true);
            p.setVelocity(p.getVelocity().setX(0.0).setY(0.1).setZ(0.0));
            p.setAllowFlight(true);
            p.setFlying(true);
        }
    }

    @EventHandler
    final void onShootBow(final EntityShootBowEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        final Player p = (Player)e.getEntity();
        if (CoreMain.vanish.contains(p)) {
            e.setCancelled(true);
        }
    }
}
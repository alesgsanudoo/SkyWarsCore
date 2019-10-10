package me.mralecroyt.Eventos.ServerOptions;

import ak.CookLoco.SkyWars.SkyWars;
import ak.CookLoco.SkyWars.player.SkyPlayer;
import me.mralecroyt.LobbyCore.CoreMain;
import me.mralecroyt.administrador.ConfigAdmin;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Leaves;

import java.util.List;

public class Worldguard implements Listener {
    @EventHandler
    public void onJoinWorldProtect(final PlayerJoinEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        final Player p = e.getPlayer();
        if (p.getLocation().getWorld().getName().equals("Lobby")) {
            e.getPlayer().setMaxHealth((double) 20);
            e.getPlayer().setHealth((double) 20);
            e.getPlayer().setFoodLevel(20);
            for (final Entity ent : e.getPlayer().getWorld().getEntities()) {
                if (ent.getType() == EntityType.PLAYER) {
                    return;
                }
                ent.remove();
            }
        }
        if (p.getLocation().getWorld().getName().equals("Lobby")) {
            final World w = e.getPlayer().getWorld();
            Chunk[] loadedChunks;
            for (int length = (loadedChunks = w.getLoadedChunks()).length, i = 0; i < length; ++i) {
                final Chunk chunk = loadedChunks[i];
                final int cx = chunk.getX() << 4;
                final int cz = chunk.getZ() << 4;
                for (int x = cx; x < cx + 16; ++x) {
                    for (int z = cz; z < cz + 16; ++z) {
                        for (int y = 0; y < 128; ++y) {
                            final Block b = w.getBlockAt(x, y, z);
                            if (b.getType() == Material.FIRE) {
                                b.setType(Material.AIR);
                            }
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void soilChangePlayer(final PlayerInteractEvent e) {
        final Player p = e.getPlayer();
        if (p.getLocation().getWorld().getName().equals("Lobby")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void soilChangeEntity(final EntityInteractEvent e) {
        final Entity p = e.getEntity();
        if (p.getLocation().getWorld().getName().equals("Lobby")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();
        e.setDeathMessage(null);
        p.setHealth(p.getMaxHealth());
        if (p.getVehicle() != null) {
            p.getVehicle().eject();
        }
        p.spigot().respawn();
        if(p.getLocation().getWorld().getName().equals("lobby")) {
            e.setDeathMessage(null);
            e.setKeepInventory(false);
            e.setDroppedExp(0);
            e.setKeepLevel(false);
            return;
        }
    }


    @EventHandler
    public void onDecay(final LeavesDecayEvent event) {
        final Block below = event.getBlock().getRelative(BlockFace.DOWN);
        final Block twoBelow = below.getRelative(BlockFace.DOWN);
        if (below.getType() == Material.GRASS || below.getType() == Material.DIRT) {
            event.setCancelled(true);
        } else if ((twoBelow.getType() == Material.GRASS || twoBelow.getType() == Material.DIRT) && below.getType() != Material.AIR) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onChangeWorldProtect(final PlayerChangedWorldEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        final Player p = e.getPlayer();
        if (p.getLocation().getWorld().getName().equals("Lobby")) {
            e.getPlayer().setMaxHealth((double) 20);
            e.getPlayer().setHealth((double) 20);
            e.getPlayer().setFoodLevel(20);
            for (final Entity ent : e.getPlayer().getWorld().getEntities()) {
                if (ent instanceof Player) {
                    return;
                }
                ent.remove();
            }
        }
        if (p.getLocation().getWorld().getName().equals("Lobby")) {
            final World w = e.getPlayer().getWorld();
            Chunk[] loadedChunks;
            for (int length = (loadedChunks = w.getLoadedChunks()).length, i = 0; i < length; ++i) {
                final Chunk chunk = loadedChunks[i];
                final int cx = chunk.getX() << 4;
                final int cz = chunk.getZ() << 4;
                for (int x = cx; x < cx + 16; ++x) {
                    for (int z = cz; z < cz + 16; ++z) {
                        for (int y = 0; y < 128; ++y) {
                            final Block b = w.getBlockAt(x, y, z);
                            if (b.getType() == Material.FIRE) {
                                b.setType(Material.AIR);
                            }
                        }
                    }
                }
            }
        }
    }



    @EventHandler
    public void onTntExplosion(final EntityExplodeEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        final Entity entity = e.getEntity();
        if (entity.getLocation().getWorld().getName().equals("Lobby")) {
            if (e.getEntity().getType() == EntityType.PRIMED_TNT) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onTntDamage(final EntityDamageByEntityEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        final Entity entity = e.getEntity();
        if (entity.getLocation().getWorld().getName().equals("Lobby")) {
            if (e.getDamager() instanceof TNTPrimed) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onFallDamage(final EntityDamageEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        final Entity entity = e.getEntity();
        if (entity.getLocation().getWorld().getName().equals("Lobby")) {
            if (e.getEntity() instanceof Player) {
                if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    e.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void onPvpDamage(final EntityDamageByEntityEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        final Entity entity = e.getEntity();
        if (entity.getLocation().getWorld().getName().equals("Lobby")) {
           if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onMobDamage(final EntityDamageByEntityEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        final Entity entity = e.getEntity();
        if (entity.getLocation().getWorld().getName().equals("Lobby")) {
            if (!(e.getDamager() instanceof Player)) {
                e.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void onBuild(final BlockPlaceEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        final Player p = e.getPlayer();
        if (p.getLocation().getWorld().getName().equals("Lobby")) {
            if (e.getBlock().getType() == Material.FIRE) {
                return;
            }
            if (e.getBlock().getType() == Material.WATER) {
                return;
            }
            if (e.getBlock().getType() == Material.LAVA) {
                return;
            }
            if (e.getPlayer().getLocation().subtract(0.0, 1.0, 0.0).getBlock().getType() == Material.AIR) {
                p.teleport(p.getLocation().subtract(0.0, 0.5, 0.0));
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(final BlockBreakEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        final Player p = e.getPlayer();
        if (p.getLocation().getWorld().getName().equals("Lobby")) {
                if (e.getPlayer().getLocation().subtract(0.0, 1.0, 0.0).getBlock().getType() == Material.AIR) {
                    p.teleport(p.getLocation().subtract(0.0, 0.5, 0.0));
                }
                e.setCancelled(true);
            }
        }


    @EventHandler
    public void onItemDrop(final PlayerDropItemEvent e) {
        final Player p = e.getPlayer();
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (p.getLocation().getWorld().getName().equals("Lobby")) {
                e.setCancelled(true);
            }
        }


    @EventHandler
    public void onItemPickup(final PlayerPickupItemEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        final Player p = e.getPlayer();
        if (p.getLocation().getWorld().getName().equals("Lobby")) {
                e.setCancelled(true);
            }
        }


    @EventHandler
    public void onMobSpawn(final CreatureSpawnEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        final Entity ent = e.getEntity();
        if (ent.getLocation().getWorld().getName().equals("Lobby")) {
            if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.CUSTOM) {
                return;
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onUseEnderpearl(final ProjectileLaunchEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        final Entity ent = e.getEntity();
        if (ent.getLocation().getWorld().getName().equals("Lobby")) {
            if (e.getEntity().getType() == EntityType.ENDER_PEARL) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onSleep(final PlayerBedEnterEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        final Player p = e.getPlayer();
        if (p.getLocation().getWorld().getName().equals("Lobby")) {
                e.setCancelled(true);
            }
        }


    @EventHandler
    public void onFire(final BlockBurnEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (e.getBlock().getWorld().getName().equals("Lobby")) {
                e.setCancelled(true);
            }
        }


    @EventHandler
    public void onEncendFire(final PlayerInteractEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        final Player p = e.getPlayer();
        if (p.getLocation().getWorld().getName().equals("Lobby")) {
            if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
                return;
            }
            final ItemStack item = e.getPlayer().getItemInHand();
            if (item.getType() == Material.FLINT_AND_STEEL || item.getType() == Material.FIREBALL || item.getType() == Material.FIRE) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlaceLava(final PlayerBucketEmptyEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        final Player p = e.getPlayer();
        if (p.getLocation().getWorld().getName().equals("Lobby")) {
            if (e.getBucket() == Material.LAVA_BUCKET) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlaceWater(final PlayerBucketEmptyEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        final Player p = e.getPlayer();
        if (p.getLocation().getWorld().getName().equals("Lobby")) {
            if (e.getBucket() == Material.WATER_BUCKET) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onLavaFlow(final BlockFromToEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (e.getBlock().getWorld().getName().equals("Lobby")) {
            if (e.getBlock().getType() == Material.LAVA || e.getBlock().getType() == Material.STATIONARY_LAVA) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onWaterFlow(final BlockFromToEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (e.getBlock().getWorld().getName().equals("Lobby")) {
            if (e.getBlock().getType() == Material.WATER || e.getBlock().getType() == Material.STATIONARY_WATER) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onUse(final PlayerInteractEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        final Player p = e.getPlayer();
        if (p.getLocation().getWorld().getName().equals("Lobby")) {
            final Block b = e.getClickedBlock();
            if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (b.getType() != Material.CHEST && b.getType() != Material.STONE_BUTTON && b.getType() != Material.WOOD_BUTTON && b.getType() != Material.ACACIA_DOOR && b.getType() != Material.BIRCH_DOOR && b.getType() != Material.DARK_OAK_DOOR && b.getType() != Material.JUNGLE_DOOR && b.getType() != Material.SPRUCE_DOOR && b.getType() != Material.TRAP_DOOR && b.getType() != Material.WOOD_DOOR && b.getType() != Material.WOODEN_DOOR && b.getType() != Material.LEVER) {
                    return;
                }
                e.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void onOpenInventories(final InventoryOpenEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (e.getPlayer().getWorld().getName().equals("Lobby")) {
            final InventoryType type = e.getInventory().getType();
            if (type == InventoryType.ANVIL || type == InventoryType.BEACON || type == InventoryType.BREWING || type == InventoryType.CRAFTING || type == InventoryType.DISPENSER || type == InventoryType.DROPPER || type == InventoryType.ENCHANTING || type == InventoryType.WORKBENCH) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onHunger(final FoodLevelChangeEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (e.getEntity().getWorld().getName().equals("Lobby")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onArmorStandPrevent(final PlayerArmorStandManipulateEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        final Player p = e.getPlayer();
        if (p.getLocation().getWorld().getName().equals("Lobby")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onHitArmorStand(final EntityDamageEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        final Entity ent = e.getEntity();
        if (ent.getLocation().getWorld().getName().equals("Lobby")) {
            if (!(e.getEntity() instanceof ArmorStand)) {
                return;
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDrown(final EntityDamageEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        final Entity ent = e.getEntity();
        if (ent.getLocation().getWorld().getName().equals("Lobby")) {
            if (!(e.getEntity() instanceof Player)) {
                return;
            }
            if (e.getCause() == EntityDamageEvent.DamageCause.DROWNING) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamage(final EntityDamageEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        final Entity ent = e.getEntity();
        if (ent.getLocation().getWorld().getName().equals("Lobby")) {
            if (!(e.getEntity() instanceof Player)) {
                return;
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onRemoveDefaultMessages(final PlayerJoinEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (c.getBoolean("AntiJoinMessage.Enable")) {
            e.setJoinMessage(null);
        }
    }

    @EventHandler
    public void onRemoveDefaultMessages(final PlayerQuitEvent e) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        if (c.getBoolean("AntiJoinMessage.Enable")) {
            e.setQuitMessage(null);
        }
    }
}

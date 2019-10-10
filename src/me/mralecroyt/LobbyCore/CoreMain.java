package me.mralecroyt.LobbyCore;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.mralecroyt.Eventos.ServerOptions.*;
import me.mralecroyt.Listener.MessengerUser;
import me.mralecroyt.Listener.SSend;
import me.mralecroyt.Listener.Utils.CustomJoins;
import me.mralecroyt.Listener.Utils.TablistAPI;
import me.mralecroyt.administrador.ConfigAdmin;
import me.mralecroyt.comandos.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import net.milkbowl.vault.chat.Chat;

import static com.avaje.ebean.Ebean.delete;


public class CoreMain extends JavaPlugin implements Listener {
    public static CoreMain instance;
    public static String TpP;
    public static String PREFIX;
    PluginManager pm;
    public List<CustomJoins> customjoins;
    public static ConfigAdmin cm;
    PluginDescriptionFile pdffile;
    public String version;
    public String nombre;
    public final Boolean papi;
    private final Boolean mvdw;
    public static String vanishenabled;
    public static String vanishdisabled;
    public static String alreadyenabled;
    public static String alreadydisabled;
    public static ArrayList<Player> vanish;
    public static Chat chat;
    public static ArrayList<MessengerUser> USER_STORAGE;
    public static ArrayList<Player> BLOCK_MSG;
    public static HashMap<Player, Player> REPLY;
    public static ArrayList<CommandSender> SOCIAL_SPY;
    public static Permission perms;
    public static ArrayList<SSend> USER_STORAGE2;


    public void onEnable() {
        final ConsoleCommandSender cs = this.getServer().getConsoleSender();
        cs.sendMessage("#######################################");
        cs.sendMessage("[LobbyCore] Cargando configs....");
        this.loadConfigs();
        getCommand("tp").setExecutor(new Teleport());
        getCommand("ss").setExecutor(new SS());
        getCommand("vanish").setExecutor(new Vanish());
        getCommand("msg").setExecutor(new MSG());
        getCommand("reply").setExecutor(new MSG());
        getCommand("broadcast").setExecutor(new BC());
        cs.sendMessage("Cargando eventos....");
        this.getServer().getPluginManager().registerEvents(new Tablist(this), this);
        this.loadEventos();
        this.deletefiles2();
        this.getCommand("fly").setExecutor(new VIPFLY());
        loadCustomJoins();
        this.getServer().getPluginManager().registerEvents(new WorldSpawn(this), this);
        this.getServer().getPluginManager().registerEvents(new Customjoinmessages(this), this);
        this.getServer().getPluginManager().registerEvents(new VanishLIstener(), this);
        cs.sendMessage("Cargando comandos....");
        WorldSpawn.SpawnManager(this);
        this.getCommand("setspawn").setExecutor(new WorldSpawn(this));
        this.TabUpdater();
        cs.sendMessage("Cargando scoreboard....");
        for (final World world : Bukkit.getWorlds()) {
            world.setStorm(false);
            world.setThundering(false);
            this.getLogger().log(Level.INFO, "Version: {0} Activado", getInstance().getDescription().getVersion());
            if (Bukkit.getPluginManager().isPluginEnabled("Vault")) {
                Bukkit.getServer().getLogger().log(Level.INFO, "Enlazado con Vault -> version: {0}", Bukkit.getPluginManager().getPlugin("Vault").getDescription().getVersion());
            } else {
                Bukkit.getServer().getLogger().log(Level.INFO, "Este plugin requiere Vault");
                Bukkit.getServer().getLogger().log(Level.INFO, "Vault Download: https://dev.bukkit.org/projects/vault");
            }
            if (this.papi) {
                if (Bukkit.getPluginManager().isPluginEnabled("PlaceHolderAPI")) {
                    Bukkit.getServer().getLogger().log(Level.INFO, "Enlazado con PlaceholderAPI -> version: {0}", PlaceholderAPIPlugin.getInstance().getDescription().getVersion());
                } else {
                    Bukkit.getServer().getLogger().log(Level.INFO, "PlaceholderAPI Download: https://www.spigotmc.org/resources/placeholderapi.6245/");
                }
            } else {
                Bukkit.getServer().getLogger().log(Level.INFO, "PlaceholderAPI desactivado en config.yml");
            }
            this.getConfig().options().copyDefaults(true);
            this.saveDefaultConfig();
            this.reloadConfig();
        }
        cs.sendMessage("Cargando plugin....");
        cs.sendMessage("Plugin completado " + this.getDescription().getVersion());
        cs.sendMessage("[LobbyCore] Plugin by MrAlecroYT");
        cs.sendMessage("#######################################");
    }


    public static String setPlaceholders(Player p, String s) {
        s = PlaceholderAPI.setPlaceholders(p, s);
        s = s.replace("%player%", p.getName());
        s = s.replace("%player-displayname%", p.getDisplayName());
        return s;
    }

    public void loadCustomJoins() {
        final FileConfiguration c = ConfigAdmin.getConfigJoins();
        this.customjoins = new ArrayList<CustomJoins>();
        final Set<String> key = c.getKeys(false);
        for (final String nodo : key) {
            final ConfigurationSection a = c.getConfigurationSection(nodo);
            if (!a.isSet("permission")) {
            }
            final CustomJoins cj = new CustomJoins (a.getString("permission"), this);
            if (a.isSet("join-message")) {
                cj.setJoinMessage(a.getString("join-message"));
            }
            this.customjoins.add(cj);
        }
    }

    private void deleteFiles(final File directory) {
        if (directory.exists()) {
            final File[] files = directory.listFiles();
            if (files != null) {
                for (final File file : files) {
                    file.delete();
                }
            }
        }
    }

    private void deleteFile(final File file) {
        if (file.exists()) {
            file.delete();
        }
    }

    public void deletefiles2() {
        this.deleteFile(new File("Lobby/uid.dat"));
        this.deleteFile(new File("Lobby/level.dat_old"));
        this.deleteFile(new File("Lobby/data/scoreboard.dat"));
    }


    public void onDisable() {
        this.deletefiles2();
    }

    private void loadEventos() {
        final PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new Clima(), this);
        pm.registerEvents(new Worldguard(), this);
        pm.registerEvents(new NoMobs(), this);
        pm.registerEvents(new VIPJOIN(), this);
        pm.registerEvents(new Voidspawn(), this);
        pm.registerEvents(new PerWorldTab(), this);
    }

    public static CoreMain getInstance() {
        return CoreMain.instance;
    }

    private void loadConfigs() {
        (cm = new ConfigAdmin()).createConfigConfig();
        (cm = new ConfigAdmin()).createConfigJoins();
    }

    public boolean maySendMessage(Player p, Player p2) {
        boolean b = true;
        if (!p.hasPermission("msg.msgblock.exempt") && CoreMain.BLOCK_MSG.contains(p2)) {
            b = false;
        }
        return b;
    }

    public CoreMain() {

        CoreMain.instance = this;
        this.papi = this.getConfig().getBoolean("PlaceholderAPI");
        this.mvdw = this.getConfig().getBoolean("MVdWPlaceholderAPI");
        this.customjoins = new ArrayList<CustomJoins>();
    }

    public static CoreMain get() {
        return getInstance();
    }

    public void updateTab(final Player p) {
        final FileConfiguration c = ConfigAdmin.getConfigConfig();
        final String headline = setPlaceholders(p, c.getString("Tablist.header").replace("&", "§").replace("%player%", p.getName()));
        final String footer = setPlaceholders(p, c.getString("Tablist.footer").replace("&", "§").replace("%player%", p.getName()));
        TablistAPI.TablistAPI(p, headline, footer);
    }

    public void TabUpdater() {
        if (getInstance().getConfig().getBoolean("Tablist.Enable")) {
            final FileConfiguration c = ConfigAdmin.getConfigConfig();
            new BukkitRunnable() {
                public void run() {
                    Bukkit.getOnlinePlayers().forEach(pl -> CoreMain.this.updateTab(pl));
                }
            }.runTaskTimerAsynchronously(get(), (long)(c.getInt("Online.Time-Update") * 20), (long)(c.getInt("Online.Time-Update") * 20));
        }
    }


    static {
        CoreMain.PREFIX = "§a§lLobby§2§lCore §8» §7";
        CoreMain.vanish = new ArrayList<>();
        CoreMain.perms = null;
        CoreMain.USER_STORAGE = new ArrayList<>();
        CoreMain.USER_STORAGE2 = new ArrayList<>();
        CoreMain.BLOCK_MSG = new ArrayList<>();
        CoreMain.REPLY = new HashMap<>();
        CoreMain.SOCIAL_SPY = new ArrayList<>();
    }


    private String fixColors(final String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String chatColors(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message.replace("\\\\n", "\n").replace("\\n", "\n").replace("&nl", "\n"));
    }
}

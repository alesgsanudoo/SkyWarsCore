package me.mralecroyt.Listener;

import me.mralecroyt.LobbyCore.CoreMain;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SSend {
        private String uuid;

        public static SSend getUser(Player p) {
            for (SSend u : CoreMain.USER_STORAGE2) {
                if (u.uuid.equals(p.getUniqueId().toString())) {
                    return u;
                }
            }
            SSend u2 = new SSend(p);
            CoreMain.USER_STORAGE2.add(u2);
            return u2;
        }

        public SSend(Player p) {
            super();
            this.uuid = p.getUniqueId().toString();
        }

        public Player getPlayer() {
            return Bukkit.getPlayer(UUID.fromString(this.uuid));
        }
}

package me.mralecroyt.Listener;

import me.mralecroyt.LobbyCore.CoreMain;
import org.bukkit.Bukkit;
import java.util.UUID;
import org.bukkit.entity.Player;

public class MessengerUser
{
    private String uuid;
    private boolean socialSpyActive;
    
    public static MessengerUser getUser(Player p) {
        for (MessengerUser u : CoreMain.USER_STORAGE) {
            if (u.uuid.equals(p.getUniqueId().toString())) {
                return u;
            }
        }
        MessengerUser u2 = new MessengerUser(p);
        CoreMain.USER_STORAGE.add(u2);
        return u2;
    }
    
    public MessengerUser(Player p) {
        super();
        this.uuid = p.getUniqueId().toString();
    }
    
    public Player getPlayer() {
        return Bukkit.getPlayer(UUID.fromString(this.uuid));
    }
    
    public boolean isSocialSpyActive() {
        return this.socialSpyActive;
    }
    
    public void setSocialSpyActive(boolean socialSpyActive) {
        this.socialSpyActive = socialSpyActive;
    }
}

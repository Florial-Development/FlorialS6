package listeners.scent;

import mysql.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class Scent implements Listener {

    @EventHandler
    public void scentlistener(PlayerInteractEvent e) {
        //just testing
        PlayerData p = new PlayerData(e.getPlayer().getUniqueId().toString(), 1, 0);


    }
}

package com.github.tanokun.ec_efc.listener;

import com.github.tanokun.ec_efc.TaktiePlugin;
import com.github.tanokun.ec_efc.data.EnderChest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class InitListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        EnderChest enderChest = TaktiePlugin.getPlugin().getDataManager().loadEnderChest(e.getPlayer().getUniqueId());
        TaktiePlugin.getPlugin().getDataManager().addEnderChest(e.getPlayer().getUniqueId(), enderChest);
        TaktiePlugin.getPlugin().getDataManager().saveEnderChest(enderChest);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        TaktiePlugin.getPlugin().getDataManager().saveEnderChest(TaktiePlugin.getPlugin().getDataManager().getEnderChest(e.getPlayer().getUniqueId()));
        TaktiePlugin.getPlugin().getDataManager().removeEnderChest(e.getPlayer().getUniqueId());
    }
}

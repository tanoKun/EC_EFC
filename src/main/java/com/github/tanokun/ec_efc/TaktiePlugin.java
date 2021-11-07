package com.github.tanokun.ec_efc;

import com.github.tanokun.ec_efc.command.OpenCommand;
import com.github.tanokun.ec_efc.data.DataManager;
import com.github.tanokun.ec_efc.data.EnderChest;
import com.github.tanokun.ec_efc.listener.InitListener;
import com.github.tanokun.ec_efc.util.command.CommandManager;
import com.github.tanokun.ec_efc.util.smart_inv.inv.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class TaktiePlugin extends JavaPlugin {
    private static TaktiePlugin taktie;

    private InventoryManager inventoryManager;

    private CommandManager commandManager;

    private DataManager dataManager;

    public static TaktiePlugin getPlugin() {
        return taktie;
    }

    @Override
    public void onEnable() {
        taktie = this;

        registryManagers();
        registryCommands();
        registryListeners();

        for (Player player : Bukkit.getOnlinePlayers()) {
            EnderChest enderChest = TaktiePlugin.getPlugin().getDataManager().loadEnderChest(player.getUniqueId());
            TaktiePlugin.getPlugin().getDataManager().addEnderChest(player.getPlayer().getUniqueId(), enderChest);
        }
    }

    @Override
    public void onDisable() {

    }

    private void registryManagers(){
        this.inventoryManager = new InventoryManager(this);

        this.commandManager = new CommandManager(this);

        this.dataManager = new DataManager();
    }

    private void registryCommands() {
        commandManager.registerCommand(new OpenCommand());
    }

    private void registryListeners() {
        Bukkit.getPluginManager().registerEvents(new InitListener(), this);
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public static void playSound(Player player, Sound sound, int volume, double v2) {
        player.playSound(player.getLocation(), sound, volume, (float) v2);
    }

}

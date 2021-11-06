package com.github.tanokun.ec_efc;

import com.github.tanokun.ec_efc.util.command.CommandManager;
import com.github.tanokun.ec_efc.util.smart_inv.inv.InventoryManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class TaktiePlugin extends JavaPlugin {
    private static TaktiePlugin taktie;

    private InventoryManager inventoryManager;

    private CommandManager commandManager;

    public static TaktiePlugin getPlugin() {
        return taktie;
    }

    @Override
    public void onEnable() {
        taktie = this;

        registryManagers();
    }

    @Override
    public void onDisable() {
    }

    private void registryManagers(){
        this.inventoryManager = new InventoryManager(this);

        this.commandManager = new CommandManager(this);
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }
}

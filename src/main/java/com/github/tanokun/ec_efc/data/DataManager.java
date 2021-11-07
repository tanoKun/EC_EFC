package com.github.tanokun.ec_efc.data;

import com.github.tanokun.ec_efc.TaktiePlugin;
import com.github.tanokun.ec_efc.util.command.TabComplete;
import com.github.tanokun.ec_efc.util.io.Config;
import com.sun.org.apache.xml.internal.security.Init;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.units.qual.C;

import javax.xml.crypto.Data;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class DataManager {
    private InitEnderChest initEnderChest = new InitEnderChest();

    private HashMap<UUID, EnderChest> enderChests = new HashMap<>();

    public DataManager(){
        Config data = new Config("EnderChest" + File.separator + "initEC.yml", TaktiePlugin.getPlugin());
        if (data.exists())
            initEnderChest.setEnderChestList((ArrayList<ArrayList<ItemStack>>) data.getConfig().getList("ec", new ArrayList<ArrayList<ItemStack>>()));
        else {
            data.createExists();
            data.getConfig().set("ec", initEnderChest.getEnderChestList());
            data.saveConfig();
        }
    }

    public EnderChest getEnderChest(UUID uuid) {return this.enderChests.get(uuid);}

    public void addEnderChest(UUID uuid, EnderChest enderChest) {this.enderChests.put(uuid, enderChest);}

    public void removeEnderChest(UUID uuid) {this.enderChests.remove(uuid);}

    public EnderChest loadEnderChest(String name) {
        return loadEnderChest(Bukkit.getPlayerUniqueId(name));
    }

    public EnderChest loadEnderChest(UUID uuid) {
        EnderChest enderChest = new EnderChest(uuid);
        Config data = new Config("EnderChest" + File.separator + uuid.toString() + ".yml", TaktiePlugin.getPlugin());
        if (!data.exists()) return enderChest;
        enderChest.setEnderChestList((ArrayList<ArrayList<ItemStack>>) data.getConfig().getList("ec", new ArrayList<ArrayList<ItemStack>>()));
        return enderChest;
    }

    public InitEnderChest getInitEnderChest() {
        return initEnderChest;
    }

    public void saveEnderChest(EnderChest enderChest) {
        Config data = new Config("EnderChest" + File.separator + enderChest.getUuid().toString() + ".yml", TaktiePlugin.getPlugin());
        data.createExists();
        data.getConfig().set("ec", enderChest.getEnderChestList());
        data.saveConfig();
    }
}

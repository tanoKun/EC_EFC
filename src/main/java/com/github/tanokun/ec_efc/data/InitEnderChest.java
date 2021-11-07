package com.github.tanokun.ec_efc.data;

import com.github.tanokun.ec_efc.TaktiePlugin;
import com.github.tanokun.ec_efc.util.ItemUtils;
import com.github.tanokun.ec_efc.util.io.Config;
import com.github.tanokun.ec_efc.util.smart_inv.inv.ClickableItem;
import com.github.tanokun.ec_efc.util.smart_inv.inv.InventoryListener;
import com.github.tanokun.ec_efc.util.smart_inv.inv.SmartInventory;
import com.github.tanokun.ec_efc.util.smart_inv.inv.contents.InventoryContents;
import com.github.tanokun.ec_efc.util.smart_inv.inv.contents.InventoryProvider;
import com.github.tanokun.ec_efc.util.smart_inv.inv.contents.Pagination;
import com.github.tanokun.ec_efc.util.smart_inv.inv.contents.SlotIterator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class InitEnderChest implements InventoryProvider {
    private UUID uuid;

    private ArrayList<ArrayList<ItemStack>> enderChestList = new ArrayList<>();

    private InventoryContents contents;

    public InitEnderChest() {
        ArrayList<ItemStack> ec = new ArrayList<>(); for (int i = 0; i < 27; i++) ec.add(new ItemStack(Material.AIR));
        ArrayList<ItemStack> ec2 = new ArrayList<>(); for (int i = 0; i < 27; i++) ec2.add(new ItemStack(Material.AIR));
        enderChestList.add(ec);
        enderChestList.add(ec2);
    }

    public SmartInventory getInv(){
        return SmartInventory.builder()
                .closeable(true)
                .cancelable(false)
                .provider(this)
                .size(4, 9)
                .title("§7§lInitEnderChest")
                .id("InitEnderChest")
                .update(false)
                .listener(getCloseListener())
                .build();
    }

    public void init(Player player, InventoryContents contents) {
        this.contents = contents;
        contents.fillRow(3, ClickableItem.of(ItemUtils.createItem(Material.BLACK_STAINED_GLASS_PANE, "", 1, false), e -> e.setCancelled(true)));

        Pagination pagination = contents.pagination();
        ClickableItem[] items = new ClickableItem[this.enderChestList.size() * 27];
        for (int n = 0; n < enderChestList.size() * 27; n++) items[n] = ClickableItem.empty(enderChestList.get(n / 27).get(n % 27));

        pagination.setItems(items);
        pagination.setItemsPerPage(27);
        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 0, 0));

        arrow(player, contents);
    }

    private void arrow(Player player, InventoryContents contents){
        Pagination pagination = contents.pagination();

        if (pagination.getPage() == 0)
            contents.set(3, 0, ClickableItem.of(ItemUtils.createItem(Material.BLACK_STAINED_GLASS_PANE, "", 1, false), e -> e.setCancelled(true)));
        else
            contents.set(3, 0, ClickableItem.of(ItemUtils.createItem(Material.SPECTRAL_ARROW,
                    pagination.getPage() + "ページに戻る", 1, true), e -> {
                contents.inventory().open(player, pagination.getPage() - 1);
                TaktiePlugin.playSound(player, Sound.ENTITY_SHULKER_OPEN, 10, 1);
            }));

        if (pagination.isLast())
            contents.set(3, 8, ClickableItem.of(ItemUtils.createItem(Material.BLACK_STAINED_GLASS_PANE, "", 1, false), e -> e.setCancelled(true)));
        else
            contents.set(3, 8, ClickableItem.of(ItemUtils.createItem(Material.SPECTRAL_ARROW,
                    (pagination.getPage() + 2) + "ページに進む", 1, true), e -> {
                contents.inventory().open(player, pagination.getPage() + 1);
                TaktiePlugin.playSound(player, Sound.ENTITY_SHULKER_OPEN, 10, 1);
            }));
    }

    private InventoryListener<InventoryCloseEvent> getCloseListener() {
        return new InventoryListener<>(InventoryCloseEvent.class, e -> {
            int p = contents.pagination().getPage();
            for (int i = 0; i < 27; i++) enderChestList.get(p).set(i, e.getInventory().getContents()[i]);
            Config data = new Config("EnderChest" + File.separator + "initEC.yml", TaktiePlugin.getPlugin());
            data.createExists();
            data.getConfig().set("ec", getEnderChestList());
            data.saveConfig();
        });
    }

    public ArrayList<ArrayList<ItemStack>> getEnderChestList() {
        return enderChestList;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setEnderChestList(ArrayList<ArrayList<ItemStack>> enderChestList) {
        this.enderChestList = enderChestList;
    }


}

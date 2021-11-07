package com.github.tanokun.ec_efc.command;

import com.github.tanokun.ec_efc.TaktiePlugin;
import com.github.tanokun.ec_efc.data.EnderChest;
import com.github.tanokun.ec_efc.util.command.*;
import com.github.tanokun.ec_efc.util.io.Config;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OpenCommand implements CommandHandler {

    @Command(parentName = "ecopen", name = "", desc = "open player's ec")
    @CommandPermission(permission = "tanokun.ec.ecopen", perDefault = PermissionDefault.OP, permissionMessage = "§c適切な操作をしてください")
    public void open(CommandSender sender, CommandContext commandContext) {

        Player target = Bukkit.getPlayer(commandContext.getArg(1, sender.getName()));
        if (target == null) {
            sender.sendMessage("§c対象のプレイヤーはオフラインか存在しません");
            return;
        }

        if (Bukkit.getPlayerUniqueId(commandContext.getArg(0, sender.getName())) == null) {
            sender.sendMessage("§cそのプレイヤーのエンダーチェストは存在しません");
            return;
        }

        UUID uuid = Bukkit.getPlayerUniqueId(commandContext.getArg(0, sender.getName()));

        Config data = new Config("EnderChest" + File.separator + uuid.toString() + ".yml", TaktiePlugin.getPlugin());
        if (!data.exists()) {
            sender.sendMessage("§cそのプレイヤーのエンダーチェストは存在しません");
            return;
        }

        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
        if (TaktiePlugin.getPlugin().getDataManager().getEnderChest(player.getUniqueId()) == null) {
            EnderChest enderChest = TaktiePlugin.getPlugin().getDataManager().loadEnderChest(player.getUniqueId());
            enderChest.getInv().open(target);
        } else {
            TaktiePlugin.getPlugin().getDataManager().getEnderChest(player.getUniqueId()).getInv().open(target);
        }
    }

    @Command(parentName = "openinit", name = "", desc = "")
    @CommandPermission(permission = "tanokun.ec.openinit", perDefault = PermissionDefault.OP, permissionMessage = "§c適切な操作をしてください")
    public void openInit(CommandSender sender, CommandContext commandContext) {
        if (!(sender instanceof Player)) {sender.sendMessage("§cプレイヤーから実行してください"); return;}

        TaktiePlugin.getPlugin().getDataManager().getInitEnderChest().getInv().open((Player) sender);
    }

    @Command(parentName = "setdata", name = "addpage", desc = "")
    @CommandPermission(permission = "tanokun.ec.addpage", perDefault = PermissionDefault.OP, permissionMessage = "§c適切な操作をしてください")
    public void addPage(CommandSender sender, CommandContext commandContext) {
        if (Bukkit.getPlayerUniqueId(commandContext.getArg(0, sender.getName())) == null) {
            sender.sendMessage("§cそのプレイヤーは存在しません");
            return;
        }

        UUID uuid = Bukkit.getPlayerUniqueId(commandContext.getArg(0, sender.getName()));

        Config data = new Config("EnderChest" + File.separator + uuid.toString() + ".yml", TaktiePlugin.getPlugin());
        if (!data.exists()) {
            sender.sendMessage("§cそのプレイヤーは存在しません");
            return;
        }

        sender.sendMessage("§a" + Bukkit.getOfflinePlayer(uuid).getName() + "のECを増設しました");
         TaktiePlugin.getPlugin().getDataManager().getEnderChest(uuid).addPage();

    }

    @Command(parentName = "setdata", name = "removepage", desc = "")
    @CommandPermission(permission = "tanokun.ec.removepage", perDefault = PermissionDefault.OP, permissionMessage = "§c適切な操作をしてください")
    public void removePage(CommandSender sender, CommandContext commandContext) {
        if (Bukkit.getPlayerUniqueId(commandContext.getArg(0, sender.getName())) == null) {
            sender.sendMessage("§cそのプレイヤーは存在しません");
            return;
        }

        UUID uuid = Bukkit.getPlayerUniqueId(commandContext.getArg(0, sender.getName()));

        Config data = new Config("EnderChest" + File.separator + uuid.toString() + ".yml", TaktiePlugin.getPlugin());
        if (!data.exists()) {
            sender.sendMessage("§cそのプレイヤーは存在しません");
            return;
        }

        sender.sendMessage("§a" + Bukkit.getOfflinePlayer(uuid).getName() + "のECを減築しました");
        TaktiePlugin.getPlugin().getDataManager().getEnderChest(uuid).removePage();

    }
}


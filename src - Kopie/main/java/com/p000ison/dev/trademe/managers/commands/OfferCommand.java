package com.p000ison.dev.trademe.managers.commands;

import com.p000ison.dev.trademe.Offer;
import com.p000ison.dev.trademe.TradeMe;
import com.p000ison.dev.trademe.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author p000ison
 */
public class OfferCommand extends BasicCommand {

    private TradeMe plugin = null;

    public OfferCommand(TradeMe plugin) {
        super("Offer");
        this.plugin = plugin;
        setDescription("Does a offer.");
        setUsage("/trademe offer §9<item> <amount> <price>");
        setArgumentRange(2, 3);
        setIdentifiers("offer");
        setPermission("trademe.command.offer");
    }

    @Override
    public boolean execute(CommandSender sender, String identifier, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (plugin.getTradeHandler().isTrading(player)) {
                String[] item = args[0].split("[:]", 2);
                if (args.length == 2) {
                    if (Util.checkItem(item[0])) {

                        plugin.getTradeHandler().requestOffer(player, Util.getPartner(plugin.getTradeHandler().getTrade(), player), new ItemStack(Material.valueOf(item[0].toUpperCase()), 1, Byte.parseByte(item[1])), Double.parseDouble(args[1]));
                    } else {
                        player.sendMessage(Util.color(String.format(plugin.getSettingsManager().getErrorInvalidItem(), args[0])));
                    }
                } else if (args.length == 3) {
                    if (Util.checkItem(item[0])) {
                        plugin.getTradeHandler().requestOffer(player, Util.getPartner(plugin.getTradeHandler().getTrade(), player), new ItemStack(Material.valueOf(item[0].toUpperCase()), Integer.parseInt(args[1]), Byte.parseByte(item[1])), Double.parseDouble(args[2]));
                    } else {
                        player.sendMessage(Util.color(String.format(plugin.getSettingsManager().getErrorInvalidItem(), args[0])));
                    }
                }
                if (Util.checkItem(args[0])) {
                    for (Player admins : plugin.getTradeHandler().getCurrentAdmins()) {
                        admins.sendMessage(String.format("%s offered %s %s %s", player.getName(), Util.getKeyfromValue(plugin.getTradeHandler().getTradeRequests(), player).getName(), args[1], args[0].toLowerCase()));
                    }
                }
            } else {
                sender.sendMessage(Util.color(plugin.getSettingsManager().getErrorNotTrading()));
            }
        } else {
            sender.sendMessage(Util.color(plugin.getSettingsManager().getErrorConsole()));
        }
        return true;
    }
}

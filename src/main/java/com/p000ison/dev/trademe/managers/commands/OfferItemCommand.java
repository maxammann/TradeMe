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
public class OfferItemCommand extends BasicCommand {

    private TradeMe plugin = null;

    public OfferItemCommand(TradeMe plugin) {
        super("OfferItem");
        this.plugin = plugin;
        setDescription("Does a offeritem.");
        setUsage("/trademe offeritem §9<item> <amount> <item> <amount>");
        setArgumentRange(2, 4);
        setIdentifiers("offeritem", "offeri", "ofi");
        setPermission("trademe.command.offeritem");
    }

    @Override
    public boolean execute(CommandSender sender, String identifier, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (plugin.getTradeHandler().isTrading(player)) {
                if (args.length == 2) {
                    String[] item = args[0].split("[:]", 2);
                    String[] itemPrice = args[1].split("[:]", 2);
                    if (Util.getMaterialFromIdOrName(item[0]) != null && Util.getMaterialFromIdOrName(itemPrice[0]) != null) {
                        if (item.length == 2 && itemPrice.length != 2) {
                            plugin.getTradeHandler().requestOfferItem(player, Util.getPartner(plugin.getTradeHandler().getTrade(), player), Util.getMaterialFromIdOrName(item[0]), 1, Byte.parseByte(item[1]), Util.getMaterialFromIdOrName(args[1]), 1, (byte) 0);
                        } else if (item.length != 2 && itemPrice.length == 2) {
                            plugin.getTradeHandler().requestOfferItem(player, Util.getPartner(plugin.getTradeHandler().getTrade(), player), Util.getMaterialFromIdOrName(item[0]), 1, (byte) 0, Util.getMaterialFromIdOrName(itemPrice[0]), 1, Byte.parseByte(itemPrice[1]));
                        } else {
                            plugin.getTradeHandler().requestOfferItem(player, Util.getPartner(plugin.getTradeHandler().getTrade(), player), Util.getMaterialFromIdOrName(args[0]), 1, (byte) 0, Util.getMaterialFromIdOrName(args[1]), 1, (byte) 0);
                        }
                    } else {
                        player.sendMessage(Util.color(String.format(plugin.getSettingsManager().getErrorInvalidItem(), item[0], itemPrice[0])));
                    }
                } else if (args.length == 4) {
                    if (Util.checkItem(args[0])) {
                        String[] item = args[0].split("[:]", 2);
                        String[] itemPrice = args[2].split("[:]", 2);
                        if (item.length == 2 && itemPrice.length != 2) {
                            plugin.getTradeHandler().requestOfferItem(player, Util.getPartner(plugin.getTradeHandler().getTrade(), player), Util.getMaterialFromIdOrName(item[0]), Integer.parseInt(args[1]), Byte.parseByte(item[1]), Util.getMaterialFromIdOrName(args[2]), Integer.parseInt(args[3]), (byte) 0);
                        } else if (item.length != 2 && itemPrice.length == 2) {
                            plugin.getTradeHandler().requestOfferItem(player, Util.getPartner(plugin.getTradeHandler().getTrade(), player), Util.getMaterialFromIdOrName(item[0]), Integer.parseInt(args[1]), (byte) 0, Util.getMaterialFromIdOrName(itemPrice[0]), Integer.parseInt(args[3]), Byte.parseByte(itemPrice[1]));
                        } else {
                            plugin.getTradeHandler().requestOfferItem(player, Util.getPartner(plugin.getTradeHandler().getTrade(), player), Util.getMaterialFromIdOrName(item[0]), Integer.parseInt(args[1]), (byte) 0, Util.getMaterialFromIdOrName(args[2]), Integer.parseInt(args[3]), (byte) 0);
                        }
                    } else {
                        player.sendMessage(Util.color(String.format(plugin.getSettingsManager().getErrorInvalidItem(), args[0])));
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

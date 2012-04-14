package com.p000ison.dev.trademe.managers.commands;

import com.p000ison.dev.trademe.TradeMe;
import com.p000ison.dev.trademe.Util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
        setIdentifiers("offer", "of");
        setPermission("trademe.command.offer");
    }

    @Override
    public boolean execute(CommandSender sender, String identifier, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (plugin.getTradeHandler().isTrading(player)) {
                String[] item = args[0].split("[:]", 2);
                if (args.length == 2) {
                    if (item.length == 2) {
                        if (Util.getMaterialFromIdOrName(item[0]) != null) {
                            plugin.getTradeHandler().requestOffer(player, Util.getPartner(plugin.getTradeHandler().getTrade(), player), Util.getMaterialFromIdOrName(item[0]), 1, Byte.parseByte(item[1]), Double.parseDouble(args[1]));
                        } else {
                            player.sendMessage(Util.color(String.format(plugin.getSettingsManager().getErrorInvalidItem(), args[0])));
                        }
                    } else if (item.length == 1) {
                        if (Util.getMaterialFromIdOrName(item[0]) != null) {
                            plugin.getTradeHandler().requestOffer(player, Util.getPartner(plugin.getTradeHandler().getTrade(), player), Util.getMaterialFromIdOrName(args[0]), 1, Byte.parseByte(item[1]), Double.parseDouble(args[1]));
                        } else {
                            player.sendMessage(Util.color(String.format(plugin.getSettingsManager().getErrorInvalidItem(), args[0])));
                        }
                    }
                } else if (args.length == 3) {
                    if (item.length == 2) {
                        if (Util.getMaterialFromIdOrName(item[0]) != null) {
                            plugin.getTradeHandler().requestOffer(player, Util.getPartner(plugin.getTradeHandler().getTrade(), player), Util.getMaterialFromIdOrName(item[0]), Integer.parseInt(args[1]), Byte.parseByte(item[1]), Double.parseDouble(args[2]));
                        } else {
                            player.sendMessage(Util.color(String.format(plugin.getSettingsManager().getErrorInvalidItem(), args[0])));
                        }
                    } else if (item.length == 1) {
                        if (Util.getMaterialFromIdOrName(item[0]) != null) {
                            plugin.getTradeHandler().requestOffer(player, Util.getPartner(plugin.getTradeHandler().getTrade(), player), Util.getMaterialFromIdOrName(args[0]), Integer.parseInt(args[1]), (byte) 0, Double.parseDouble(args[2]));
                        } else {
                            player.sendMessage(Util.color(String.format(plugin.getSettingsManager().getErrorInvalidItem(), args[0])));
                        }
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

package com.p000ison.dev.trademe.managers.commands;

import com.p000ison.dev.trademe.TradeMe;
import com.p000ison.dev.trademe.Util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author p000ison
 */
public class YesCommand extends BasicCommand {

    private TradeMe plugin = null;

    public YesCommand(TradeMe plugin) {
        super("Yes");
        this.plugin = plugin;
        setDescription("Accepts a offer.");
        setUsage("/trademe yes §9- Accepts Offers");
        setArgumentRange(0, 0);
        setIdentifiers("yes");
        setPermission("trademe.command.yes");
    }

    @Override
    public boolean execute(CommandSender sender, String identifier, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (plugin.getTradeHandler().isTrading(player)) {
                if (plugin.getTradeHandler().getOfferRequest().containsKey(player)) {
                    if (plugin.getTradeHandler().getOfferRequest().get(player).price != -1) {
                        plugin.getTradeHandler().sendItem(Util.getPartner(plugin.getTradeHandler().getTrade(), player), player, plugin.getTradeHandler().getOfferRequest().get(player).itemstack, plugin.getTradeHandler().getOfferRequest().get(player).price, null);
                        player.sendMessage(Util.color(plugin.getSettingsManager().getMessageAcceptOffer()));
                        Util.getPartner(plugin.getTradeHandler().getTrade(), player).sendMessage(Util.color(String.format(plugin.getSettingsManager().getMessageOtherAcceptOffer(), player.getName())));
                    } else {
                        plugin.getTradeHandler().sendItem(Util.getPartner(plugin.getTradeHandler().getTrade(), player), player, plugin.getTradeHandler().getOfferRequest().get(player).itemstack, -1D ,plugin.getTradeHandler().getOfferRequest().get(player).itemPrice);
                        player.sendMessage(Util.color(plugin.getSettingsManager().getMessageAcceptOffer()));
                        Util.getPartner(plugin.getTradeHandler().getTrade(), player).sendMessage(Util.color(String.format(plugin.getSettingsManager().getMessageOtherAcceptOffer(), player.getName())));
                    }
                    plugin.getTradeHandler().getOfferRequest().remove(player);
                } else {
                    player.sendMessage(Util.color(plugin.getSettingsManager().getErrorNoOffer()));
                }
            } else {
                player.sendMessage(Util.color(plugin.getSettingsManager().getErrorNotTrading()));
            }
        } else {
            sender.sendMessage(Util.color(plugin.getSettingsManager().getErrorConsole()));
        }
        return true;
    }
}
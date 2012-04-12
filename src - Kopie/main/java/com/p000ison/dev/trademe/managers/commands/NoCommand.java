package com.p000ison.dev.trademe.managers.commands;

import com.p000ison.dev.trademe.TradeMe;
import com.p000ison.dev.trademe.Util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author p000ison
 */
public class NoCommand extends BasicCommand {

    private TradeMe plugin = null;

    public NoCommand(TradeMe plugin) {
        super("No");
        this.plugin = plugin;
        setDescription("Denies a offer.");
        setUsage("/trademe no §9- Denies Offers");
        setArgumentRange(0, 0);
        setIdentifiers("no");
        setPermission("trademe.command.no");
    }

    @Override
    public boolean execute(CommandSender sender, String identifier, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (plugin.getTradeHandler().isTrading(player)) {
                if (plugin.getTradeHandler().getOfferRequest().containsKey(player)) {
                    plugin.getTradeHandler().getOfferRequest().remove(player);
                    player.sendMessage(Util.color(plugin.getSettingsManager().getMessageDeniedOffer()));
                    Util.getPartner(plugin.getTradeHandler().getTrade(), player).sendMessage(Util.color(String.format(plugin.getSettingsManager().getMessageOtherDeniedOffer(), player.getName())));
                } else if (plugin.getTradeHandler().getOfferRequest().containsKey(Util.getPartner(plugin.getTradeHandler().getTrade(), player))) {
                    plugin.getTradeHandler().getOfferRequest().remove(Util.getPartner(plugin.getTradeHandler().getTrade(), player));
                    player.sendMessage(Util.color(plugin.getSettingsManager().getMessageDeniedOffer()));
                    Util.getPartner(plugin.getTradeHandler().getTrade(), player).sendMessage(Util.color(String.format(plugin.getSettingsManager().getMessageOtherDeniedOffer(), player.getName())));
                } else {
                    player.sendMessage(Util.color(plugin.getSettingsManager().getErrorNoOffer()));
                }

                for (Player admins : plugin.getTradeHandler().getCurrentAdmins()) {
                    admins.sendMessage(Util.color(String.format("%s denies the offer from %s", player.getName(), Util.getPartner(plugin.getTradeHandler().getTrade(), player).getName())));
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
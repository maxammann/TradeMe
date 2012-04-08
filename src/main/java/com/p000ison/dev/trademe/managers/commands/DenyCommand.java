package com.p000ison.dev.trademe.managers.commands;

import com.p000ison.dev.trademe.TradeMe;
import com.p000ison.dev.trademe.Util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author p000ison
 */
public class DenyCommand extends BasicCommand {

    private TradeMe plugin = null;

    public DenyCommand(TradeMe plugin) {
        super("Deny");
        this.plugin = plugin;
        setDescription("Denys a request.");
        setUsage("/trademe deny §9- Denies Trade-Requests");
        setArgumentRange(0, 0);
        setIdentifiers("deny", "leave");
        setPermission("trademe.command.deny");
    }

    @Override
    public boolean execute(CommandSender sender, String identifier, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (plugin.getTradeHandler().isTrading(player)) {
                if (plugin.getTradeHandler().getTrade().containsKey(player)) {
                    player.sendMessage(Util.color(plugin.getSettingsManager().getMessageCancelTrade()));
                    Util.getPartner(plugin.getTradeHandler().getTrade(), player).sendMessage(Util.color(plugin.getSettingsManager().getMessageOtherCancelTrade()));
                    plugin.getTradeHandler().cancelTradeFromRequester(player);
                } else if (plugin.getTradeHandler().getTrade().containsValue(player)) {
                    player.sendMessage(Util.color(plugin.getSettingsManager().getMessageCancelTradeRequest()));
                    Util.getPartner(plugin.getTradeHandler().getTrade(), player).sendMessage(Util.color(String.format(plugin.getSettingsManager().getMessageOtherCancelTradeRequest(), player.getName())));
                    plugin.getTradeHandler().cancelTradeFromTarget(player);
                }
            } else if (plugin.getTradeHandler().isInRequest(player)) {
                if (plugin.getTradeHandler().getTradeRequests().containsKey(player)) {
                    player.sendMessage(Util.color(plugin.getSettingsManager().getMessageDenyTrade()));
                    Util.getPartner(plugin.getTradeHandler().getTradeRequests(), player).sendMessage(Util.color(String.format(plugin.getSettingsManager().getMessageOtherDenyTrade(), player.getName())));
                    plugin.getTradeHandler().cancelRequestFromRequester(player);
                } else if (plugin.getTradeHandler().getTradeRequests().containsValue(player)) {
                    player.sendMessage(Util.color(plugin.getSettingsManager().getMessageDenyTradeRequest()));
                    Util.getPartner(plugin.getTradeHandler().getTradeRequests(), player).sendMessage(Util.color(String.format(plugin.getSettingsManager().getMessageOtherDenyTradeRequest(), player.getName())));
                    plugin.getTradeHandler().cancelRequestFromTarget(player);
                }
            }
            for (Player admins : plugin.getTradeHandler().getCurrentAdmins()) {
                admins.sendMessage(String.format("%s denied a request from %s", player.getName(), Util.getKeyfromValue(plugin.getTradeHandler().getTradeRequests(), player).getName()));
            }
        } else {
            sender.sendMessage(Util.color(plugin.getSettingsManager().getErrorConsole()));
        }
        return true;
    }
}
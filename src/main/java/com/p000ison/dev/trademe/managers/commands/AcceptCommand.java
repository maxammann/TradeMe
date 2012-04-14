package com.p000ison.dev.trademe.managers.commands;

import com.p000ison.dev.trademe.TradeMe;
import com.p000ison.dev.trademe.Util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author p000ison
 */
public class AcceptCommand extends BasicCommand {

    private TradeMe plugin = null;

    public AcceptCommand(TradeMe plugin) {
        super("Accept");
        this.plugin = plugin;
        setDescription("Accepts a request.");
        setUsage("/trademe accept §9- Accepts Trade-Requests");
        setArgumentRange(0, 0);
        setIdentifiers("accept", "ac");
        setPermission("trademe.command.accept");
    }

    @Override
    public boolean execute(CommandSender sender, String identifier, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (plugin.getTradeHandler().getTradeRequests().containsValue(player)) {
                plugin.getTradeHandler().getTrade().put(Util.getKeyfromValue(plugin.getTradeHandler().getTradeRequests(), player), player);
                sender.sendMessage(Util.color(plugin.getSettingsManager().getMessageAcceptRequest()));
                Util.getKeyfromValue(plugin.getTradeHandler().getTradeRequests(), player).sendMessage(Util.color(String.format(plugin.getSettingsManager().getMessageOtherAcceptRequest(), sender.getName())));
                for (Player admins : plugin.getTradeHandler().getCurrentAdmins()) {
                    admins.sendMessage(Util.color(String.format("%s accepted a request from %s", player.getName(), Util.getKeyfromValue(plugin.getTradeHandler().getTradeRequests(), player).getName())));
                }
            } else {
                sender.sendMessage(Util.color(plugin.getSettingsManager().getErrorNoRequestToTrade()));
            }
        } else {
            sender.sendMessage(Util.color(plugin.getSettingsManager().getErrorConsole()));
        }
        return true;
    }
}
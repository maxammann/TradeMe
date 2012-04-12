package com.p000ison.dev.trademe.managers.commands;

import com.p000ison.dev.trademe.TradeMe;
import com.p000ison.dev.trademe.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author p000ison
 */
public class RequestCommand extends BasicCommand {

    private TradeMe plugin = null;

    public RequestCommand(TradeMe plugin) {
        super("Request");
        this.plugin = plugin;
        setDescription("Requests a trade.");
        setUsage("/trademe request §9<player>");
        setArgumentRange(1, 1);
        setIdentifiers("request");
        setPermission("trademe.command.request");
    }

    @Override
    public boolean execute(CommandSender sender, String identifier, String[] args) {
        if (args.length == 1) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (!plugin.getTradeHandler().isTrading(player) && !plugin.getTradeHandler().isTrading(Bukkit.getPlayer(args[0])))
                if (Bukkit.getPlayer(args[0]) != player) {
                    if (Bukkit.getPlayer(args[0]) != null && Bukkit.getPlayer(args[0]).isOnline()) {
                        if (player.getLocation().distance(Bukkit.getPlayer(args[0]).getLocation()) <= plugin.getSettingsManager().getDistanceMinimum()) {
                            plugin.getTradeHandler().requestTrade(player, Bukkit.getPlayer(args[0]));
                            for (Player admins : plugin.getTradeHandler().getCurrentAdmins()) {
                                admins.sendMessage(String.format("%s sent a request to %s", player.getName(), args[0]));
                            }
                        } else {
                            sender.sendMessage(Util.color(plugin.getSettingsManager().getErrorTooFar()));
                        }

                    } else {
                        sender.sendMessage(Util.color(String.format(plugin.getSettingsManager().getErrorNotAValidPlayer(), args[0])));
                    }
                } else {
                    sender.sendMessage(Util.color(plugin.getSettingsManager().getErrorTradingWithYourself()));
                }

            } else {
                sender.sendMessage(Util.color(plugin.getSettingsManager().getErrorConsole()));
            }
        }
        return true;
    }
}

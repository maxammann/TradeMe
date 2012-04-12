package com.p000ison.dev.trademe.managers.commands;

import com.p000ison.dev.trademe.TradeMe;
import com.p000ison.dev.trademe.Util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author p000ison
 */
public class AdminCommand extends BasicCommand {

    private TradeMe plugin = null;

    public AdminCommand(TradeMe plugin) {
        super("Admin");
        this.plugin = plugin;
        setDescription("Admin Command.");
        setUsage("/trademe admin §9<watch/unwatch>");
        setArgumentRange(1, 1);
        setIdentifiers("admin");
        setPermission("trademe.command.admin");
    }

    @Override
    public boolean execute(CommandSender sender, String identifier, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("watch")) {
                    plugin.getTradeHandler().getCurrentAdmins().add(player);
                    sender.sendMessage(Util.color(plugin.getSettingsManager().getMessageWatch()));
                } else if (args[0].equalsIgnoreCase("unwatch")) {
                    plugin.getTradeHandler().getCurrentAdmins().remove(player);
                    sender.sendMessage(Util.color(plugin.getSettingsManager().getMessageUnWatch()));
                }
            }
        } else {
            sender.sendMessage(Util.color(plugin.getSettingsManager().getErrorConsole()));
        }
        return true;
    }
}
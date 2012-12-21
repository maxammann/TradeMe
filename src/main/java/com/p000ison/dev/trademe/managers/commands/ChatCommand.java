package com.p000ison.dev.trademe.managers.commands;

import com.p000ison.dev.trademe.TradeMe;
import com.p000ison.dev.trademe.Util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author p000ison
 */
public class ChatCommand extends BasicCommand {

    private TradeMe plugin = null;

    public ChatCommand(TradeMe plugin)
    {
        super("Chat");
        this.plugin = plugin;
        setDescription("Chat with your partner");
        setUsage("/trademe chat");
        setArgumentRange(1, 1);
        setIdentifiers("chat", "ch");
        setPermission("trademe.command.chat");
    }

    @Override
    public boolean execute(CommandSender sender, String identifier, String[] args)
    {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (plugin.getTradeHandler().isTrading(player)) {
                player.sendMessage(String.format("[%s]: %s", player.getName(), args[0]));
                Util.getPartner(plugin.getTradeHandler().getTrade(), player).sendMessage(String.format("[%s]: %s", player.getName(), args[0]));
            } else {
                player.sendMessage(Util.color(plugin.getSettingsManager().getErrorNotTrading()));
            }
        } else {
            sender.sendMessage(plugin.getSettingsManager().getErrorConsole());
        }
        return true;
    }
}

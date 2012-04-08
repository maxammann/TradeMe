package com.p000ison.dev.trademe.managers.commands;

import com.p000ison.dev.trademe.TradeMe;
import com.p000ison.dev.trademe.Util;
import org.bukkit.command.CommandSender;

/**
 *
 * @author p000ison
 */
public class ReloadCommand extends BasicCommand {

    private TradeMe plugin = null;

    public ReloadCommand(TradeMe plugin) {
        super("Reload");
        this.plugin = plugin;
        setDescription("Reloads the Config.");
        setUsage("/trademe reload");
        setArgumentRange(0, 0);
        setIdentifiers("reload");
        setPermission("trademe.command.reload");
    }

    @Override
    public boolean execute(CommandSender sender, String identifier, String[] args) {
        final long startTime = System.nanoTime();
        plugin.getSettingsManager().load();
        final long endTime = System.nanoTime();
        sender.sendMessage(Util.color(String.format(plugin.getSettingsManager().getMessageReload(), (endTime - startTime) / 1000)));
        return true;
    }
}

package com.p000ison.dev.trademe.managers.commands;

import com.p000ison.dev.trademe.TradeMe;
import com.p000ison.dev.trademe.Util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author p000ison
 */
public class YesCommand extends BasicCommand {

    private TradeMe plugin = null;

    public YesCommand(TradeMe plugin)
    {
        super("Yes");
        this.plugin = plugin;
        setDescription("Accepts a offer.");
        setUsage("/trademe yes §9- Accepts Offers");
        setArgumentRange(0, 0);
        setIdentifiers("yes");
        setPermission("trademe.command.yes");
    }

    @Override
    public boolean execute(CommandSender sender, String identifier, String[] args)
    {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (plugin.getTradeHandler().isTrading(player)) {
                if (plugin.getTradeHandler().getOfferRequest().containsKey(player)) {
                    if (plugin.getTradeHandler().getOfferRequest().get(player).price != -1) {
                        plugin.getTradeHandler().sendItem(Util.getPartner(plugin.getTradeHandler().getTrade(), player), player, new ItemStack(plugin.getTradeHandler().getOfferRequest().get(player).mat, plugin.getTradeHandler().getOfferRequest().get(player).amount, (short) 0, plugin.getTradeHandler().getOfferRequest().get(player).data), plugin.getTradeHandler().getOfferRequest().get(player).price, null);
                        player.sendMessage(Util.color(plugin.getSettingsManager().getMessageAcceptOffer()));
                        Util.getPartner(plugin.getTradeHandler().getTrade(), player).sendMessage(Util.color(String.format(plugin.getSettingsManager().getMessageOtherAcceptOffer(), player.getName())));
                    } else {
                        plugin.getTradeHandler().sendItem(Util.getPartner(plugin.getTradeHandler().getTrade(), player), player, new ItemStack(plugin.getTradeHandler().getOfferRequest().get(player).mat, plugin.getTradeHandler().getOfferRequest().get(player).amount, (short) 0, plugin.getTradeHandler().getOfferRequest().get(player).data), -1, new ItemStack(plugin.getTradeHandler().getOfferRequest().get(player).matPrice, plugin.getTradeHandler().getOfferRequest().get(player).amountPrice, (short) 0, plugin.getTradeHandler().getOfferRequest().get(player).dataPrice));
                        System.out.println(plugin.getTradeHandler().getOfferRequest().get(player).matPrice + "" + plugin.getTradeHandler().getOfferRequest().get(player).dataPrice);
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
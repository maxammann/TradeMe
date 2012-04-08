package com.p000ison.dev.trademe.managers;

import com.p000ison.dev.trademe.Offer;
import com.p000ison.dev.trademe.TradeMe;
import com.p000ison.dev.trademe.Util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author p000ison
 */
public class TradeHandler {

    private TradeMe plugin;

    public TradeHandler(TradeMe plugin) {
        this.plugin = plugin;
    }
    private HashMap<Player, Offer> offerRequest = new HashMap<Player, Offer>();
    private HashMap<Player, Player> trade = new HashMap<Player, Player>();
    private HashMap<Player, Player> tradeRequests = new HashMap<Player, Player>();
    private List<Player> CurrentAdmins = new ArrayList<Player>();

    public boolean isTrading(Player player) {
        return trade.containsKey(player) || trade.containsValue(player);
    }
    
    public boolean isInRequest(Player player) {
        return tradeRequests.containsKey(player) || tradeRequests.containsValue(player);
    }

    public void requestTrade(Player requester, Player target) {
        tradeRequests.put(requester, target);
        requester.sendMessage(Util.color(plugin.getSettingsManager().getMessageRequestSent()));
        target.sendMessage(Util.color(String.format(plugin.getSettingsManager().getMessageGotRequest(), requester.getName())));
    }

    public void requestOffer(Player requester, Player target, ItemStack item, double price) {
        if (requester.getInventory().contains(item.getType(), item.getAmount())) {
            offerRequest.put(target, new Offer(item, requester, price));
            requester.sendMessage(Util.color(String.format(plugin.getSettingsManager().getMessageOfferSent(), item.getType().toString().toLowerCase(), item.getAmount(), price)));
            target.sendMessage(Util.color(String.format(plugin.getSettingsManager().getMessageGotOffer(), requester.getName(), item.getType().toString().toLowerCase(), item.getAmount(), price)));
        } else {
            requester.sendMessage(Util.color(plugin.getSettingsManager().getErrorNotEnoughtItems()));
        }
    }

    public void sendItem(Player from, Player target, ItemStack item, double price) {
        if (from.getInventory().contains(item.getType(), item.getAmount())) {
            TradeMe.deposit(from, price);
            TradeMe.withdraw(target, price);
            Util.remove(from.getInventory(), item, item.getAmount(), item.getDurability());
            target.getInventory().addItem(item);
            //target.sendMessage(String.format("You got a trade request from %s", from.getName()));
        }
    }

    public void cancelTradeFromRequester(Player requester) {
        if (trade.containsKey(requester)) {
            trade.remove(requester);
        }
    }

    public void cancelTradeFromTarget(Player target) {
        if (trade.containsValue(target)) {
            trade.values().remove(target);
        }
    }

    public void cancelRequestFromRequester(Player requester) {
        if (tradeRequests.containsKey(requester)) {
            tradeRequests.remove(requester);
        }
    }

    public void cancelRequestFromTarget(Player target) {
        if (tradeRequests.containsValue(target)) {
            tradeRequests.values().remove(target);
        }
    }

    /**
     * @return the trade
     */
    public HashMap<Player, Player> getTrade() {
        return trade;
    }

    /**
     * @return the requests
     */
    public HashMap<Player, Player> getTradeRequests() {
        return tradeRequests;
    }

    /**
     * @return the offerRequest
     */
    public HashMap<Player, Offer> getOfferRequest() {
        return offerRequest;
    }

    /**
     * @return the CurrentAdmins
     */
    public List<Player> getCurrentAdmins() {
        return CurrentAdmins;
    }
}

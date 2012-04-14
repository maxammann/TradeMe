package com.p000ison.dev.trademe.managers;

import com.p000ison.dev.trademe.Offer;
import com.p000ison.dev.trademe.TradeMe;
import com.p000ison.dev.trademe.Util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Material;
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

    public void requestOffer(Player requester, Player target, Material mat, int amount, byte data, double price) {
        if (Util.contains(requester.getInventory(), mat, amount, data)) {
            if (TradeMe.hasMoney(target, price)) {
                if (target.getInventory().firstEmpty() != -1) {
                    offerRequest.put(target, new Offer(mat, amount, data, requester, price, null, 0, (byte) -1));
                    requester.sendMessage(Util.color(String.format(plugin.getSettingsManager().getMessageOfferSent(), mat.toString(), data, amount, price)));
                    target.sendMessage(Util.color(String.format(plugin.getSettingsManager().getMessageGotOffer(), requester.getName(), mat.toString(), data, amount, price)));
                } else {
                    requester.sendMessage(Util.color(String.format(plugin.getSettingsManager().getErrorOtherNotEnoughtItems(), target.getName())));
                }
            } else {
                requester.sendMessage(Util.color(String.format(plugin.getSettingsManager().getErrorOtherNotEnoughtMoney(), target.getName())));
            }
        } else {
            requester.sendMessage(Util.color(plugin.getSettingsManager().getErrorNotEnoughtItems()));
        }
    }

    public void requestOfferItem(Player requester, Player target, Material mat, int amount, byte data, Material matPrice, int amountPrice, byte dataPrice) {
        if (Util.contains(requester.getInventory(), mat, amount, data)) {
            if (Util.contains(target.getInventory(), matPrice, amountPrice, dataPrice)) {
                if (target.getInventory().firstEmpty() != -1) {
                    if (requester.getInventory().firstEmpty() != -1) {
                        offerRequest.put(target, new Offer(mat, amount, data, requester, -1, matPrice, amountPrice, dataPrice));
                        requester.sendMessage(Util.color(String.format(plugin.getSettingsManager().getMessageItemOfferSent(), mat.toString(), data, amount, matPrice.toString(), data, amountPrice)));
                        target.sendMessage(Util.color(String.format(plugin.getSettingsManager().getMessageGotItemOffer(), requester.getName(), mat.toString(), data, amount, matPrice.toString(), data, amountPrice)));
                    } else {
                        requester.sendMessage(Util.color(plugin.getSettingsManager().getErrorNotEnoughtPlace()));
                    }
                } else {
                    requester.sendMessage(Util.color(plugin.getSettingsManager().getErrorOtherNotEnoughtPlace()));
                }
            } else {
                requester.sendMessage(Util.color(String.format(plugin.getSettingsManager().getErrorOtherNotEnoughtItems(), target.getName())));
            }
        } else {
            requester.sendMessage(Util.color(plugin.getSettingsManager().getErrorNotEnoughtItems()));
        }
    }

    public void sendItem(Player from, Player target, ItemStack item, double price, ItemStack itemPrice) {
        if (price != -1) {
            TradeMe.deposit(from, price);
            TradeMe.withdraw(target, price);
            Util.remove(from.getInventory(), item, item.getAmount(), (short) -1);
            target.getInventory().addItem(item);
        } else if (itemPrice != null && price == -1) {
            if (Util.contains(target.getInventory(), itemPrice.getType(), itemPrice.getAmount(), itemPrice.getData().getData())) {
                if (Util.contains(from.getInventory(), item.getType(), item.getAmount(), item.getData().getData())) {
                    Util.remove(target.getInventory(), itemPrice, itemPrice.getAmount(), (short) -1);
                    from.getInventory().addItem(itemPrice);
                    Util.remove(from.getInventory(), item, item.getAmount(), (short) -1);
                    target.getInventory().addItem(item);
                } else {
                    target.sendMessage(Util.color(plugin.getSettingsManager().getErrorNotEnoughtItems()));
                }
            } else {
                target.sendMessage(Util.color(String.format(plugin.getSettingsManager().getErrorOtherNotEnoughtItems(), from.getName())));
            }
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

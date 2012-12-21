package com.p000ison.dev.trademe.listener;

import com.p000ison.dev.trademe.TradeMe;
import com.p000ison.dev.trademe.Util;
import com.p000ison.dev.trademe.managers.TradeHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@SuppressWarnings("unused")
public class TMPlayerListener implements Listener {

    private TradeMe plugin;

    public TMPlayerListener(TradeMe plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerMove(PlayerMoveEvent event)
    {
        if (plugin.getSettingsManager().getDistanceMinimum() != -1) {
            Player player = event.getPlayer();
            TradeHandler handler = plugin.getTradeHandler();

            if (handler.isTrading(player) || handler.isInRequest(player)) {
                if ((Util.getPartner(handler.getTrade(), player) != null && player.getLocation().distance(Bukkit.getPlayer(Util.getPartner(handler.getTrade(), player).getName()).getLocation()) >= plugin.getSettingsManager().getDistanceMinimum())
                        || (Util.getPartner(handler.getTradeRequests(), player) != null && player.getLocation().distance(Bukkit.getPlayer(Util.getPartner(handler.getTradeRequests(), player).getName()).getLocation()) >= plugin.getSettingsManager().getDistanceMinimum())) {

                    player.sendMessage(Util.color(plugin.getSettingsManager().getErrorTooFar()));
                    if (Util.getPartner(handler.getTrade(), player) != null) {
                        Util.getPartner(handler.getTrade(), player).sendMessage(Util.color(plugin.getSettingsManager().getErrorTooFar()));
                    } else {
                        Util.getPartner(handler.getTradeRequests(), player).sendMessage(Util.color(plugin.getSettingsManager().getErrorTooFar()));
                    }
                    if (handler.isTrading(player)) {
                        if (handler.getTrade().containsKey(player)) {
                            handler.cancelTradeFromRequester(player);
                        } else if (handler.getTrade().containsValue(player)) {
                            handler.cancelTradeFromTarget(player);
                        }
                    } else if (handler.isInRequest(player)) {
                        if (handler.getTradeRequests().containsKey(player)) {
                            handler.cancelRequestFromRequester(player);
                        } else if (handler.getTradeRequests().containsValue(player)) {
                            handler.cancelRequestFromTarget(player);
                        }
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        TradeHandler handler = plugin.getTradeHandler();

        if (handler.isTrading(player) || handler.isInRequest(player)) {
            if (Util.getPartner(handler.getTrade(), player) == null
                    || Util.getPartner(handler.getTradeRequests(), player) == null) {
                return;
            }
            Util.getPartner(handler.getTrade(), player).sendMessage(Util.color(String.format(plugin.getSettingsManager().getErrorPlayerNotAviable(), player.getName())));

            if (handler.getTrade().containsKey(player)) {
                handler.cancelTradeFromRequester(player);
            } else if (handler.getTradeRequests().containsKey(player)) {
                handler.cancelRequestFromRequester(player);
            }

            if (handler.getTrade().containsValue(player)) {
                handler.cancelTradeFromTarget(player);
            } else if (handler.getTradeRequests().containsValue(player)) {
                handler.cancelRequestFromTarget(player);
            }
        }
    }
}

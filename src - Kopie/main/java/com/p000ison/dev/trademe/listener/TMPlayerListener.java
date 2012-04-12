package com.p000ison.dev.trademe.listener;

import com.p000ison.dev.trademe.TradeMe;
import com.p000ison.dev.trademe.Util;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 *
 * @author p000ison
 */
public class TMPlayerListener implements Listener {

    private TradeMe plugin;

    public TMPlayerListener(TradeMe plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerMove(PlayerMoveEvent event) {
        if (plugin.getSettingsManager().getDistanceMinimum() != 0.0) {
            if (plugin.getTradeHandler().isTrading(event.getPlayer()) || plugin.getTradeHandler().isInRequest(event.getPlayer())) {
                if ((Util.getPartner(plugin.getTradeHandler().getTrade(), event.getPlayer()) != null && event.getPlayer().getLocation().distance(Bukkit.getPlayer(Util.getPartner(plugin.getTradeHandler().getTrade(), event.getPlayer()).getName()).getLocation()) >= plugin.getSettingsManager().getDistanceMinimum())
                        || (Util.getPartner(plugin.getTradeHandler().getTradeRequests(), event.getPlayer()) != null && event.getPlayer().getLocation().distance(Bukkit.getPlayer(Util.getPartner(plugin.getTradeHandler().getTradeRequests(), event.getPlayer()).getName()).getLocation()) >= plugin.getSettingsManager().getDistanceMinimum())) {

                    event.getPlayer().sendMessage(Util.color(plugin.getSettingsManager().getErrorTooFar()));
                    if (Util.getPartner(plugin.getTradeHandler().getTrade(), event.getPlayer()) != null) {
                        Util.getPartner(plugin.getTradeHandler().getTrade(), event.getPlayer()).sendMessage(Util.color(plugin.getSettingsManager().getErrorTooFar()));
                    } else {
                        Util.getPartner(plugin.getTradeHandler().getTradeRequests(), event.getPlayer()).sendMessage(Util.color(plugin.getSettingsManager().getErrorTooFar()));
                    }
                    if (plugin.getTradeHandler().isTrading(event.getPlayer())) {
                        if (plugin.getTradeHandler().getTrade().containsKey(event.getPlayer())) {
                            plugin.getTradeHandler().cancelTradeFromRequester(event.getPlayer());
                        } else if (plugin.getTradeHandler().getTrade().containsValue(event.getPlayer())) {
                            plugin.getTradeHandler().cancelTradeFromTarget(event.getPlayer());
                        }
                    } else if (plugin.getTradeHandler().isInRequest(event.getPlayer())) {
                        if (plugin.getTradeHandler().getTradeRequests().containsKey(event.getPlayer())) {
                            plugin.getTradeHandler().cancelRequestFromRequester(event.getPlayer());
                        } else if (plugin.getTradeHandler().getTradeRequests().containsValue(event.getPlayer())) {
                            plugin.getTradeHandler().cancelRequestFromTarget(event.getPlayer());
                        }
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (plugin.getTradeHandler().isTrading(event.getPlayer()) || plugin.getTradeHandler().isInRequest(event.getPlayer())) {
            if (Util.getPartner(plugin.getTradeHandler().getTrade(), event.getPlayer()) == null
                    || Util.getPartner(plugin.getTradeHandler().getTradeRequests(), event.getPlayer()) == null) {
                return;
            }
            Util.getPartner(plugin.getTradeHandler().getTrade(), event.getPlayer()).sendMessage(Util.color(String.format(plugin.getSettingsManager().getErrorPlayerNotAviable(), event.getPlayer().getName())));

            if (plugin.getTradeHandler().getTrade().containsKey(event.getPlayer())) {
                plugin.getTradeHandler().cancelTradeFromRequester(event.getPlayer());
            } else if (plugin.getTradeHandler().getTradeRequests().containsKey(event.getPlayer())) {
                plugin.getTradeHandler().cancelRequestFromRequester(event.getPlayer());
            }

            if (plugin.getTradeHandler().getTrade().containsValue(event.getPlayer())) {
                plugin.getTradeHandler().cancelTradeFromTarget(event.getPlayer());
            } else if (plugin.getTradeHandler().getTradeRequests().containsValue(event.getPlayer())) {
                plugin.getTradeHandler().cancelRequestFromTarget(event.getPlayer());
            }
        }
    }
}

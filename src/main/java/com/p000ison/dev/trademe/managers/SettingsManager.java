package com.p000ison.dev.trademe.managers;

import com.p000ison.dev.trademe.TradeMe;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author p000ison
 */
public final class SettingsManager {

    private TradeMe plugin;
    private File main;
    private FileConfiguration config;
    private String MessageRequestSent;
    private String MessageGotRequest;
    private String MessageOfferSent;
    private String MessageGotOffer;
    private String MessageWatch;
    private String MessageUnWatch;
    private String ErrorNotEnoughtItems;
    private String ErrorNoRequestToTrade;
    private String ErrorConsole;
    private String ErrorTooFar;
    private String MessageAcceptRequest;
    private String MessageAcceptOffer;
    private String MessageOtherAcceptRequest;
    private String MessageOtherAcceptOffer;
    private String ErrorNotTrading;
    private String ErrorTradingWithYourself;
    private String ErrorNotAValidPlayer;
    private String MessageDenyTrade;
    private String MessageCancelTrade;
    private String MessageDenyTradeRequest;
    private String MessageCancelTradeRequest;
    private String MessageOtherDenyTrade;
    private String MessageOtherCancelTrade;
    private String MessageOtherDenyTradeRequest;
    private String MessageOtherCancelTradeRequest;
    private String MessageReload;
    private String MessageDeniedOffer;
    private String MessageOtherDeniedOffer;
    private String ErrorNoOffer;
    private String ErrorInvalidItem;
    private String ErrorPlayerNotAviable;
    private double DistanceMinimum;

    /**
     *
     */
    public SettingsManager(TradeMe plugin) {
        this.plugin = plugin;
        config = plugin.getConfig();
        main = new File(getPlugin().getDataFolder() + File.separator + "config.yml");
        load();
    }

    /**
     * Load the configuration
     */
    public void load() {
        boolean exists = (main).exists();

        if (exists) {
            try {
                getConfig().options().copyDefaults(true);
                getConfig().load(main);
            } catch (Exception ex) {
                plugin.getLogger().log(Level.WARNING, "Loading the config failed!:{0}", ex.getMessage());
            }
        } else {
            getConfig().options().copyDefaults(true);

        }
        getConfig().options().header("-- TradeMe Configuration --");
        MessageRequestSent = getConfig().getString("Messages.RequestSent");
        MessageGotRequest = getConfig().getString("Messages.GotRequest");
        MessageOfferSent = getConfig().getString("Messages.OfferSent");
        MessageGotOffer = getConfig().getString("Messages.GotOffer");
        MessageWatch = getConfig().getString("Messages.Watch");
        MessageUnWatch = getConfig().getString("Messages.UnWatch");
        ErrorNotEnoughtItems = getConfig().getString("Messages.NotEnoughtItems");
        ErrorNoRequestToTrade = getConfig().getString("Messages.NoRequestToTrade");
        ErrorConsole = getConfig().getString("Messages.Console");
        ErrorTooFar = getConfig().getString("Messages.TooFar");
        MessageAcceptRequest = getConfig().getString("Messages.AcceptRequest");
        MessageAcceptOffer = getConfig().getString("Messages.AcceptOffer");
        ErrorNotTrading = getConfig().getString("Messages.NotTrading");
        ErrorTradingWithYourself = getConfig().getString("Messages.TradingWithYourself");
        ErrorNotAValidPlayer = getConfig().getString("Messages.NotAValidPlayer");
        MessageDenyTrade = getConfig().getString("Messages.DenyTrade");
        MessageCancelTrade = getConfig().getString("Messages.CancelTrade");
        MessageDenyTradeRequest = getConfig().getString("Messages.DenyTradeRequest");
        MessageCancelTradeRequest = getConfig().getString("Messages.CancelTradeRequest");
        MessageOtherDenyTrade = getConfig().getString("Messages.OtherDenyTrade");
        MessageOtherCancelTrade = getConfig().getString("Messages.OtherCancelTrade");
        MessageOtherDenyTradeRequest = getConfig().getString("Messages.OtherDenyTradeRequest");
        MessageOtherCancelTradeRequest = getConfig().getString("Messages.OtherCancelTradeRequest");
        MessageReload = getConfig().getString("Messages.Reload");
        MessageOtherAcceptRequest = getConfig().getString("Messages.OtherAcceptRequest");
        MessageOtherAcceptOffer = getConfig().getString("Messages.OtherAcceptOffer");
        MessageDeniedOffer = getConfig().getString("Messages.DeniedOffer");
        MessageOtherDeniedOffer = getConfig().getString("Messages.OtherDeniedOffer");
        ErrorNoOffer = getConfig().getString("Messages.NoOffer");
        ErrorInvalidItem = getConfig().getString("Messages.InvalidItem");
        ErrorPlayerNotAviable = getConfig().getString("Messages.PlayerNotAviable");
        DistanceMinimum = getConfig().getDouble("Settings.DistanceMinimum");

        save();
    }

    public void save() {
        try {
            getConfig().save(main);
        } catch (IOException ex) {
            plugin.getLogger().log(Level.WARNING, "Saving the config failed!:{0}", ex.getMessage());
        }
    }

    /**
     * @return the plugin
     */
    public TradeMe getPlugin() {
        return plugin;
    }

    /**
     * @return the config
     */
    public FileConfiguration getConfig() {
        return config;
    }

    /**
     * @return the MessageRequestSent
     */
    public String getMessageRequestSent() {
        return MessageRequestSent;
    }

    /**
     * @return the MessageGotRequest
     */
    public String getMessageGotRequest() {
        return MessageGotRequest;
    }

    /**
     * @return the MessageOfferSent
     */
    public String getMessageOfferSent() {
        return MessageOfferSent;
    }

    /**
     * @return the MessageGotOffer
     */
    public String getMessageGotOffer() {
        return MessageGotOffer;
    }

    /**
     * @return the MessageWatch
     */
    public String getMessageWatch() {
        return MessageWatch;
    }

    /**
     * @return the MessageUnWarch
     */
    public String getMessageUnWatch() {
        return MessageUnWatch;
    }

    /**
     * @return the ErrorNotEnoughtItems
     */
    public String getErrorNotEnoughtItems() {
        return ErrorNotEnoughtItems;
    }

    /**
     * @return the ErrorNoRequestToTrade
     */
    public String getErrorNoRequestToTrade() {
        return ErrorNoRequestToTrade;
    }

    /**
     * @return the ErrorConsole
     */
    public String getErrorConsole() {
        return ErrorConsole;
    }

    /**
     * @return the ErrorTooFar
     */
    public String getErrorTooFar() {
        return ErrorTooFar;
    }

    /**
     * @return the MessageAcceptRequest
     */
    public String getMessageAcceptRequest() {
        return MessageAcceptRequest;
    }

    /**
     * @return the MessageAcceptOffer
     */
    public String getMessageAcceptOffer() {
        return MessageAcceptOffer;
    }

    /**
     * @return the ErrorNotTrading
     */
    public String getErrorNotTrading() {
        return ErrorNotTrading;
    }

    /**
     * @return the ErrorTradingWithYourself
     */
    public String getErrorTradingWithYourself() {
        return ErrorTradingWithYourself;
    }

    /**
     * @return the ErrorNotAValidPlayer
     */
    public String getErrorNotAValidPlayer() {
        return ErrorNotAValidPlayer;
    }

    /**
     * @return the MessageCancelTradeRequest
     */
    public String getMessageDenyTrade() {
        return MessageDenyTrade;
    }

    /**
     * @return the MessageCancelTrade
     */
    public String getMessageCancelTrade() {
        return MessageCancelTrade;
    }

    /**
     * @return the MessageDenyTradeRequest
     */
    public String getMessageDenyTradeRequest() {
        return MessageDenyTradeRequest;
    }

    /**
     * @return the MessageCancelTradeRequest
     */
    public String getMessageCancelTradeRequest() {
        return MessageCancelTradeRequest;
    }

    /**
     * @return the MessageReload
     */
    public String getMessageReload() {
        return MessageReload;
    }

    /**
     * @return the MessageOtherAcceptRequest
     */
    public String getMessageOtherAcceptRequest() {
        return MessageOtherAcceptRequest;
    }

    /**
     * @return the MessageOtherAcceptOffer
     */
    public String getMessageOtherAcceptOffer() {
        return MessageOtherAcceptOffer;
    }

    /**
     * @return the MessageOtherDenyTrade
     */
    public String getMessageOtherDenyTrade() {
        return MessageOtherDenyTrade;
    }

    /**
     * @return the MessageOtherCancelTrade
     */
    public String getMessageOtherCancelTrade() {
        return MessageOtherCancelTrade;
    }

    /**
     * @return the MessageOtherDenyTradeRequest
     */
    public String getMessageOtherDenyTradeRequest() {
        return MessageOtherDenyTradeRequest;
    }

    /**
     * @return the MessageOtherCancelTradeRequest
     */
    public String getMessageOtherCancelTradeRequest() {
        return MessageOtherCancelTradeRequest;
    }

    /**
     * @return the MessageDeniedOffer
     */
    public String getMessageDeniedOffer() {
        return MessageDeniedOffer;
    }

    /**
     * @return the MessageOtherDeniedOffer
     */
    public String getMessageOtherDeniedOffer() {
        return MessageOtherDeniedOffer;
    }

    /**
     * @return the ErrorNoOffer
     */
    public String getErrorNoOffer() {
        return ErrorNoOffer;
    }

    /**
     * @return the ErrorInvalidItem
     */
    public String getErrorInvalidItem() {
        return ErrorInvalidItem;
    }

    /**
     * @return the ErrorPlayerNotAviable
     */
    public String getErrorPlayerNotAviable() {
        return ErrorPlayerNotAviable;
    }

    /**
     * @return the DistanceMinimum
     */
    public double getDistanceMinimum() {
        return DistanceMinimum;
    }
}
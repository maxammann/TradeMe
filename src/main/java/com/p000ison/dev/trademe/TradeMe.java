package com.p000ison.dev.trademe;

import com.p000ison.dev.trademe.listener.TMPlayerListener;
import com.p000ison.dev.trademe.managers.CommandHandler;
import com.p000ison.dev.trademe.managers.SettingsManager;
import com.p000ison.dev.trademe.managers.TradeHandler;
import com.p000ison.dev.trademe.managers.commands.*;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author p000ison
 */
public class TradeMe extends JavaPlugin {

    private static final Logger log = Logger.getLogger("Minecraft");
    private SettingsManager SettingsManager;
    private static Permission perms = null;
    private static Economy econ = null;
    private TradeHandler tradeHandler;
    private CommandHandler commandHandler = new CommandHandler();

    @Override
    public void onDisable()
    {
    }

    @Override
    public void onEnable()
    {
        final long startTime = System.currentTimeMillis();

        SettingsManager = new SettingsManager(this);
        tradeHandler = new TradeHandler(this);

        registerCommands();
        setupMetrics();

        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            setupPermissions();
            log.info("Hooked Vault for Permission-Support!");
        }

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new TMPlayerListener(this), this);

        if (!setupEconomy()) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
        }
        final long endTime = System.currentTimeMillis();
        log.info(String.format("Enabled (%s s)", ((double) (endTime - startTime)) / 1000.0D));
    }

    private void registerCommands()
    {
        commandHandler = new CommandHandler();

        commandHandler.addCommand(new HelpCommand(this));
        commandHandler.addCommand(new OfferCommand(this));
        commandHandler.addCommand(new YesCommand(this));
        commandHandler.addCommand(new AcceptCommand(this));
        commandHandler.addCommand(new DenyCommand(this));
        commandHandler.addCommand(new ReloadCommand(this));
        commandHandler.addCommand(new RequestCommand(this));
        commandHandler.addCommand(new ChatCommand(this));
        commandHandler.addCommand(new NoCommand(this));
        commandHandler.addCommand(new OfferItemCommand(this));
    }

    public void setupMetrics()
    {
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e) {
            log.severe(String.format("Could not send Plugin-Stats: %s", e.getMessage()));
        }
    }

    private boolean setupPermissions()
    {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    private boolean setupEconomy()
    {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        return commandHandler.dispatch(sender, cmd, commandLabel, args);
    }

    public static void deposit(Player player, double value)
    {
        EconomyResponse r = econ.depositPlayer(player.getName(), value);
        if (r.transactionSuccess()) {
            player.sendMessage(Util.color(String.format("&4You were given %s and now have %s", econ.format(r.amount), econ.format(r.balance))));
        } else {
            player.sendMessage(Util.color(String.format("&4An error occured: %s", r.errorMessage)));
        }
    }

    public static boolean hasMoney(Player player, double price)
    {
        return econ.has(player.getName(), price);
    }

    public static void withdraw(Player player, double value)
    {
        EconomyResponse r = econ.withdrawPlayer(player.getName(), value);
        if (r.transactionSuccess()) {
            player.sendMessage(Util.color(String.format("&4You were withdrawen %s and now have %s", econ.format(r.amount), econ.format(r.balance))));
        } else {
            player.sendMessage(Util.color(String.format("&4An error occured: %s", r.errorMessage)));
        }
    }

    public static boolean hasPermission(Player player, String permission)
    {
        if (perms != null) {
            return perms.has(player, permission);
        }
        return player.hasPermission(permission);
    }

    /**
     * @return the commandHandler
     */
    public CommandHandler getCommandHandler()
    {
        return commandHandler;
    }

    /**
     * @return the tradeHandler
     */
    public TradeHandler getTradeHandler()
    {
        return tradeHandler;
    }

    /**
     * @return the SettingsManager
     */
    public SettingsManager getSettingsManager()
    {
        return SettingsManager;
    }
}

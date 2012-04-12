package com.p000ison.dev.trademe.managers.commands;

import org.bukkit.command.CommandSender;

/**
 *
 * @author p000ison
 */
public interface Command {

    public void cancelInteraction(CommandSender executor);

    public boolean execute(CommandSender executor, String identifier, String[] args);

    public String getDescription();

    public String[] getIdentifiers();

    public int getMaxArguments();

    public int getMinArguments();

    public String getName();

    public String[] getNotes();

    public String getPermission();

    public String getUsage();

    public boolean isIdentifier(CommandSender executor, String input);

    public boolean isInProgress(CommandSender executor);

    public boolean isInteractive();

    public boolean isShownOnHelpMenu();

}
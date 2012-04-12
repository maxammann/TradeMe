package com.p000ison.dev.trademe.managers.commands;

import com.p000ison.dev.trademe.TradeMe;
import com.p000ison.dev.trademe.Util;
import java.util.Iterator;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author p000ison
 */
public class TestCommand extends BasicCommand {
    
    private TradeMe plugin = null;
    
    public TestCommand(TradeMe plugin) {
        super("Test");
        this.plugin = plugin;
        setDescription("Test");
        setUsage("/trademe test");
        setArgumentRange(0, 0);
        setIdentifiers("test");
        setPermission("trademe.command.test");
    }
    
    @Override
    public boolean execute(CommandSender sender, String identifier, String[] args) {
        ItemStack item = new ItemStack(Material.LOG);
        Player p = (Player) sender;
        System.out.println(item);
        System.out.println(p.getItemInHand());
        if (contains(p.getInventory(), Material.LOG, 3, (byte)0x2)) {
            sender.sendMessage("You have 3 Birch!");
        } else {
            sender.sendMessage("You dont have 3 Birch!");
        }
        return true;
    }
    
    public static boolean contains(Inventory inv, Material mat, int amount, byte data) {
        int i = 0;
        
        for (ItemStack is : inv.getContents()) {
            if (is != null) {
                if (is.getType() == mat && is.getData().getData() == data) {
                    i = i + is.getAmount();
                }
            }
        }
        return i >= amount;
    }
}

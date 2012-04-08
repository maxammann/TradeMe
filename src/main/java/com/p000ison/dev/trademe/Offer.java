package com.p000ison.dev.trademe;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author p000ison
 */
public class Offer {
    public ItemStack itemstack;
    public Player requester;
    public double price;
    
    public Offer(ItemStack itemstack, Player requester, double price) {
        this.itemstack = itemstack;
        this.requester = requester;
        this.price = price;
    }
}

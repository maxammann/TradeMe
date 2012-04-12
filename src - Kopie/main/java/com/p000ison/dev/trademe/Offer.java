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
    public ItemStack itemPrice;
    
    public Offer(ItemStack itemstack, Player requester, double price, ItemStack itemPrice) {
        this.itemPrice = itemPrice;
        this.itemstack = itemstack;
        this.requester = requester;
        this.price = price;
    }
}

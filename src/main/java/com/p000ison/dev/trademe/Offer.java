package com.p000ison.dev.trademe;

import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 *
 * @author p000ison
 */
public class Offer {
    public Material mat;
    public int amount;
    public byte data;
    
    public Player requester;
    
    public double price;
    public Material matPrice;
    public int amountPrice;
    public byte dataPrice;
    
    public Offer(Material mat, int amount, byte data, Player requester, double price, Material matPrice, int amountPrice, byte dataPrice) {
        this.mat = mat;
        this.amount = amount;
        this.data = data;
        this.requester = requester;
        this.price = price;
        this.matPrice = matPrice;
        this.amountPrice = amountPrice;
        this.dataPrice = dataPrice;
    }
}

package com.p000ison.dev.trademe;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map.Entry;

public class Util {

    private Util()
    {
    }

    public static Player getKeyfromValue(HashMap<Player, Player> map, Player value)
    {
        for (Entry<Player, Player> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static Player getKeyfromValue(HashMap<Player, Offer> map, Offer value)
    {
        for (Entry<Player, Offer> entry : map.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static Player getPartner(HashMap<Player, Player> map, Player player)
    {
        if (map.containsKey(player)) {
            return map.get(player);
        } else if (map.containsValue(player)) {
            return Util.getKeyfromValue(map, player);
        }
        return null;
    }

    public static boolean checkItem(String itemname)
    {
        return Material.matchMaterial(itemname) != null;
    }

    /**
     * Author: Acrobot
     */
    public static int remove(Inventory inv, ItemStack item, int amount, short durability)
    {
        amount = (amount > 0 ? amount : 1);
        Material itemMaterial = item.getType();

        int first = inv.first(itemMaterial);
        if (first == -1) {
            return amount;
        }

        for (int slot = first; slot < inv.getSize(); slot++) {
            if (amount <= 0) {
                return 0;
            }

            ItemStack currentItem = inv.getItem(slot);
            if (currentItem == null || currentItem.getType() == Material.AIR) {
                continue;
            }

            if (equals(currentItem, item, durability)) {
                int currentAmount = currentItem.getAmount();
                if (amount == currentAmount) {
                    currentItem = null;
                    amount = 0;
                } else if (amount < currentAmount) {
                    currentItem.setAmount(currentAmount - amount);
                    amount = 0;
                } else {
                    currentItem = null;
                    amount -= currentAmount;
                }
                inv.setItem(slot, currentItem);
            }
        }
        return amount;
    }

    private static boolean equals(ItemStack i, ItemStack item, short durability)
    {
        return i != null
                && i.getType() == item.getType()
                && i.getData().getData() == item.getData().getData()
                && (durability == -1 || i.getDurability() == durability);
    }

    public static String color(String text)
    {
        return text.replaceAll("&(?=[0-9a-fA-FkK])", "\u00a7");
    }

    public static boolean contains(Inventory inv, Material mat, int amount, byte data)
    {
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

    public static int add(Inventory inv, ItemStack item, int amount)
    {
        amount = (amount > 0 ? amount : 1);
        ItemStack itemstack = new ItemStack(item.getType(), amount, item.getDurability());
        itemstack.addEnchantments(item.getEnchantments());

        HashMap<Integer, ItemStack> items = inv.addItem(itemstack);
        amount = 0;
        for (ItemStack toAdd : items.values()) {
            amount += toAdd.getAmount();
        }

        return amount;
    }

    public static Material getMaterialFromIdOrName(String name)
    {
        if (checkItem(name) || checkItem(Material.getMaterial(Integer.parseInt(name)).name())) {
            if (name.matches("[0-9]+")) {
                return Material.getMaterial(Integer.parseInt(name));
            } else if (name.matches("[a-zA-Z]+")) {
                return Material.getMaterial(name.toUpperCase());
            }
            return null;
        }
        return null;
    }
}
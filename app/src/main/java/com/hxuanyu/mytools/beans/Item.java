package com.hxuanyu.mytools.beans;

import com.hxuanyu.mytools.inter.ItemInter;

public class Item {
    private String itemName;
    private ItemInter itemInter;

    public Item(String itemName, ItemInter itemInter) {
        this.itemName = itemName;
        this.itemInter = itemInter;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public ItemInter getItemInter() {
        return itemInter;
    }

    public void setItemInter(ItemInter itemInter) {
        this.itemInter = itemInter;
    }
}

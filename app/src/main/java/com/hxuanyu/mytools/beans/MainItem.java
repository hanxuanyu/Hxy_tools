package com.hxuanyu.mytools.beans;

import com.hxuanyu.mytools.inter.ItemInter;

public class MainItem {
    private String itemName;
    private String itemInfo;
    private ItemInter itemInter;

    public MainItem(String itemName, String itemInfo,ItemInter itemInter) {
        this.itemName = itemName;
        this.itemInter = itemInter;
        this.itemInfo = itemInfo;
    }

    public String getItemInfo() {
        return itemInfo;
    }

    public void setItemInfo(String itemInfo) {
        this.itemInfo = itemInfo;
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

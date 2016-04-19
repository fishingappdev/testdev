package com.dev.fishingapp.support;

/**
 * Created by user on 4/18/2016.
 */
public class NavDrawerItem {
    public String name;

    public NavDrawerItem(String navMenuTitle) {
        this.name=navMenuTitle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package com.hertzai.hevolve.models.appModel;

import android.widget.ImageView;

public class SectionItem {
    private String desc;
    private ImageView menus;

    public SectionItem(String desc, ImageView menus) {
        this.desc = desc;
        this.menus = menus;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ImageView getMenus() {
        return menus;
    }

    public void setMenus(ImageView menus) {
        this.menus = menus;
    }
}

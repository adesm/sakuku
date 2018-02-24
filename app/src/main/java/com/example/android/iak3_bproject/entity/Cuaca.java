package com.example.android.iak3_bproject.entity;

/**
 * Created by adesm on 03/02/18.
 */

public class Cuaca {
    private String title;
    private String desc;

    public Cuaca(String title, String desc) {
        this.title = title;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

package com.example.vasyldvorak_hw_android_java_2.model;

import androidx.annotation.StyleRes;

import com.example.vasyldvorak_hw_android_java_2.R;

public enum Theme {

    ONE(R.style.MyStyle, R.string.my_style, "themeone"),
    TWO(R.style.AppThemeLight, R.string.light_theme, "themetwo"),
    THREE(R.style.AppThemeDark, R.string.dark_theme, "themethree");
    @StyleRes
    private int themeRes;
    @StyleRes
    private int title;
    private String key;

    Theme(int themeRes, int title, String key) {
        this.themeRes = themeRes;
        this.title = title;
        this.key = key;
    }

    public int getThemeRes() {
        return themeRes;
    }

    public int getTitle() {
        return title;
    }

    public String getKey() {
        return key;
    }
}

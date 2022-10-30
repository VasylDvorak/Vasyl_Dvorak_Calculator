package com.example.vasyldvorak_hw_android_java_2.model;

import java.util.List;

public interface ThemeRepository {
    Theme getSavedTheme();

    void saveTheme(Theme theme);

    List<Theme> getAll();
}

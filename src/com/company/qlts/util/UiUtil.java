package com.company.qlts.util;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Arrays;

public class UiUtil {

    private static final String FONT_FAMILY = findFont();

    private static String findFont() {
        // Ưu tiên font đẹp
        String[] preferred = {"Segoe UI", "San Francisco", "Roboto", "Arial"};
        String[] available = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getAvailableFontFamilyNames();
        for (String p : preferred) {
            if (Arrays.asList(available).contains(p)) return p;
        }
        return "Arial";
    }

    public static Font font(int style, int size) {
        return new Font(FONT_FAMILY, style, size);
    }

    public static Font regular(int size) { return font(Font.PLAIN, size); }
    public static Font bold(int size)    { return font(Font.BOLD, size); }
}
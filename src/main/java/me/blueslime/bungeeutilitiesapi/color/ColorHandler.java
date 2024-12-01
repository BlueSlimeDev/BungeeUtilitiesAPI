package me.blueslime.bungeeutilitiesapi.color;

import me.blueslime.bungeeutilitiesapi.color.types.BungeeColor;

public abstract class ColorHandler {

    private static ColorHandler INSTANCE = null;

    public static ColorHandler get() {
        if (INSTANCE == null) {
            INSTANCE = create();
        }
        return INSTANCE;
    }

    private static ColorHandler create() {
        return new BungeeColor();
    }

    public abstract String execute(String text);

    public static String convert(String text) {
        return get().execute(text);
    }

}
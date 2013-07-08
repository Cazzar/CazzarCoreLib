package net.cazzar.corelib.lib;

import org.lwjgl.util.Color;

public enum Colours {
    BLACK('0', 0, 0, 0),
    DARK_BLUE('1', 0, 0, 170),
    DARK_GREEN('2', 0, 70, 0),
    DARK_AQUA('3', 0, 170, 170),
    DARK_RED('4', 170, 0, 0),
    PURPLE('5', 170, 0, 170),
    GOLD('6', 255, 170, 0),
    GRAY('7', 170, 170, 170),
    DARK_GRAY('8', 85, 85, 85),
    BLUE('9', 85, 85, 255),
    GREEN('a', 85, 255, 85),
    AQUA('b', 85, 255, 255),
    RED('c', 255, 85, 85),
    LIGHT_PURPLE('d', 255, 85, 255),
    YELLOW('e', 255, 255, 85),
    WHITE('f', 255, 255, 255),

    OBFUSCATED('k'),
    BOLD('l'),
    STRIKE_THROUGH('m'),
    UNDERLINE('n'),
    RESET('r');

    static char section = '§';
    private final char code;
    private final int red, green, blue;

    private Colours(char code, int red, int green, int blue) {
        this.code = code;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    private Colours(char code) {
        this(code, 0, 0, 0);
    }

    public Color getColour() {
        return new Color(red, green, blue);
    }

    @Override
    public String toString() {
        return String.valueOf(section) + code;
    }

    public String getHex() {
        return String.format("%s%s%sFF", red, green, blue);
    }
}
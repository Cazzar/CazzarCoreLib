/*
 * Copyright (C) 2013 cazzar
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see [http://www.gnu.org/licenses/].
 */

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

    private static char section = '\u00a7';
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

    /**
     * Get the LWJGL {@link Color} of the Minecraft colours
     *
     * @return The LWJGL colour
     */
    public Color getColour() {
        return new Color(red, green, blue);
    }

    @Override
    public String toString() {
        return String.valueOf(section) + code;
    }

    /**
     * Get the hexadecimal version of the colour
     *
     * @return A {@link String} containing the colour in hex format <i>RRGGBBAA</i>
     */
    public String getHex() {
        return String.format("%s%s%sFF", red, green, blue);
    }
}
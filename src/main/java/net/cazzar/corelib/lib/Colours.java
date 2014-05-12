/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Cayde Dixon
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.cazzar.corelib.lib;

import org.lwjgl.util.Color;

@SuppressWarnings("UnusedDeclaration")
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
        char section = '\u00a7';
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
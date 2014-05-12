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

package net.cazzar.corelib.asm;


public class MethodDescription {
    private final String name;
    private final String desc;

    /**
     * A description about a method
     *
     * @param left  the name
     * @param right the description
     */
    public MethodDescription(String left, String right) {
        this.name = left;
        this.desc = right;
    }

    /**
     * Get the name of the function
     *
     * @return The name of the function
     */
    public String getName() {
        return name;
    }

    /**
     * Get the description of the function
     *
     * @return The function description
     */
    public String getDesc() {
        return desc;
    }

    @Override
    public int hashCode() {
        return name.hashCode() ^ desc.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof MethodDescription)) return false;
        MethodDescription pair = (MethodDescription) o;
        return this.name.equals(pair.getName()) && this.desc.equals(pair.getDesc());
    }

    @Override
    public String toString() {
        return getName().concat(" ").concat(getDesc());
    }
}

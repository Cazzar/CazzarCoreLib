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

import java.lang.reflect.Field;

/**
 * A description of a java field for use in the Transformers
 */
public class FieldDescription {
    private final String classname;
    private final String name;

    public FieldDescription(String classname, String name) {
        this.classname = classname;
        this.name = name;
    }

    /**
     * Get the field name
     *
     * @return the name of the field
     */
    public String getName() {
        return name;
    }

    /**
     * Get the class name the field resides in
     *
     * @return the class name
     */
    public String getClassname() {
        return classname;
    }

    /**
     * Get the java class name used for the class loader
     *
     * @return the Canonacal Classname
     */
    public String getCanonacalClassname() {
        return classname.replace("/", ".");
    }

    /**
     * Get the name and class name combined
     *
     * @return the full name of the class with the name concatenated
     */
    @SuppressWarnings("UnusedDeclaration")
    public String getFullName() {
        return getCanonacalClassname().concat(name);
    }

    /**
     * get the Java {@link java.lang.reflect.Field} for the field reference
     *
     * @return the Java field
     */
    @SuppressWarnings("UnusedDeclaration")
    public Field getField() {
        try {
            Class<?> cl = Class.forName(getCanonacalClassname());

            return cl.getDeclaredField(getName());
        } catch (ClassNotFoundException e) {
            return null;
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    @Override
    public int hashCode() {
        return classname.hashCode() ^ name.hashCode();
    }

    @Override
    public String toString() {
        return getCanonacalClassname();
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof FieldDescription && equals((FieldDescription) o);

    }

    public boolean equals(FieldDescription fieldDescription) {
        return getName().equals(fieldDescription.getName())
                && getClassname().equals(fieldDescription.getClassname());
    }
}

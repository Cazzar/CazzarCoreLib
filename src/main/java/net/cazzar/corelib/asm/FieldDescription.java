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

package net.cazzar.corelib.asm;

import java.lang.reflect.Field;

/**
 * @Author: Cayde
 */
public class FieldDescription {
    private final String classname;
    private final String name;

    public FieldDescription(String classname, String name) {
        this.classname = classname;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getClassname() {
        return classname;
    }

    public String getCanonacalClassname() {
        return classname.replace("/", ".");
    }

    public String getFullName() {
        return getCanonacalClassname().concat(name);
    }

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
        if (o == null) return false;
        if (!(o instanceof FieldDescription)) return false;

        return equals((FieldDescription) o);
    }

    public boolean equals(FieldDescription fieldDescription) {
        return getName().equals(fieldDescription.getName())
                && getClassname().equals(fieldDescription.getClassname());
    }
}

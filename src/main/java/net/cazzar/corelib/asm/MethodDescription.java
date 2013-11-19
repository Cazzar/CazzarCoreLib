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


public class MethodDescription {
    private final String name;
    private final String desc;

    public MethodDescription(String left, String right) {
        this.name = left;
        this.desc = right;
    }

    public String getName() {
        return name;
    }

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

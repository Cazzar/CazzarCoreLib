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

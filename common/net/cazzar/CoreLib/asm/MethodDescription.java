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

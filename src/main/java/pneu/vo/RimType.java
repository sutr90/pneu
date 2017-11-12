package pneu.vo;

public enum RimType {
    STEEL("Ocel"),
    ALUMINUM("Hliník"),
    NONE("Není");

    private final String name;

    RimType(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}

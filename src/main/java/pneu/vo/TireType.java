package pneu.vo;

public enum TireType {
    SUMMER("Letní"),
    WINTER("Zimní"),
    UNIVERSAL("Uni");

    private final String name;

    TireType(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }
}

package pneu.model;

public class Tire implements Slot {
    private final int id;

    public Tire(int id) {
        this.id = id;
    }

    @Override
    public int getWidth() {
        return 1;
    }

    public int getId() {
        return id;
    }
}

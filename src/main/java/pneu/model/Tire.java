package pneu.model;

public class Tire implements Slot {
    private final int id;
    private final Rack rack;
    public Tire(int id, Rack rack) {
        this.id = id;
        this.rack = rack;
    }

    @Override
    public int getWidth() {
        return 300;
    }

    @Override
    public Rack getRack() {
        return rack;
    }

    public int getId() {
        return id;
    }
}

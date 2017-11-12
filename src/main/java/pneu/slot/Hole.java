package pneu.slot;

import pneu.rack.Rack;

public class Hole implements Slot {
    private final int width;
    private final Rack rack;

    public Hole(int width, Rack rack) {
        this.width = width;
        this.rack = rack;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public Rack getRack() {
        return rack;
    }
}

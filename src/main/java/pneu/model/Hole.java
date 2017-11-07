package pneu.model;

public class Hole implements Slot {
    private final int width;

    Hole(int width) {
        this.width = width;
    }

    @Override
    public int getWidth() {
        return width;
    }
}

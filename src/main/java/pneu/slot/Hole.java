package pneu.slot;

import com.fasterxml.jackson.annotation.JsonBackReference;
import pneu.rack.Rack;

public class Hole implements Slot {
    private int width;
    @JsonBackReference(value = "rackContent")
    private Rack rack;

    public Hole(){}

    public Hole(int width, Rack rack) {
        this.width = width;
        this.rack = rack;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setRack(Rack rack) {
        this.rack = rack;
    }

    @Override
    public Rack getRack() {
        return rack;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}

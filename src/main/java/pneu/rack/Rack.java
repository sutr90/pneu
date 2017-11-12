package pneu.rack;

import pneu.slot.Hole;
import pneu.slot.Slot;

import java.util.ArrayList;
import java.util.List;

public class Rack {
    private final String name;
    private final List<Slot> content;

    public Rack(String name) {
        this.name = name;
        content = new ArrayList<>();
        content.add(new Hole(9860, this));
    }

    public String getName() {
        return name;
    }

    public List<Slot> getContent() {
        return content;
    }

}

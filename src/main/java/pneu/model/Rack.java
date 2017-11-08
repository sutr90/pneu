package pneu.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Rack {
    private final String name;
    private final List<Slot> content;

    Rack(String name) {
        this.name = name;
        content = new LinkedList<>();
        content.add(new Hole(9860));
    }

    public String getName() {
        return name;
    }

    public List<Slot> getContent() {
        return content;
    }

}

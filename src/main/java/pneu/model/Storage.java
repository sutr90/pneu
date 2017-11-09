package pneu.model;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private List<Rack> racks;

    private static String[] labels = {"A", "B", "C", "D", "E"};

    public Storage() {
        racks = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            racks.add(new Rack(labels[i]));
        }
    }

    public List<Rack> getRacks() {
        return racks;
    }

    void setRacks(List<Rack> racks) {
        this.racks = racks;
    }
}

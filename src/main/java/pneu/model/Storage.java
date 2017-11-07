package pneu.model;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private List<Rack> racks;

    public Storage() {

    }

    private static String[] labels = {"A", "B", "C", "D", "E"};

    Storage(int rackCount) {
        racks = new ArrayList<>(rackCount);
        for (int i = 0; i < rackCount; i++) {
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

package pneu.storage;

import pneu.rack.Rack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Storage {
    private Set<String> manufacturers;

    private List<Rack> racks;

    private static String[] labels = {"A", "B", "C", "D", "E"};

    public Storage() {
        racks = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            racks.add(new Rack(labels[i]));
        }

        manufacturers = new HashSet<>();
    }

    public List<Rack> getRacks() {
        return racks;
    }

    void setRacks(List<Rack> racks) {
        this.racks = racks;
    }

    public Set<String> getManufacturers() {
        return manufacturers;
    }
}

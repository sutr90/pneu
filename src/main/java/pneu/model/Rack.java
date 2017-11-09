package pneu.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Rack {
    private final String name;
    private final ObservableList<Slot> content;

    Rack(String name) {
        this.name = name;
        content = FXCollections.observableArrayList();
        content.add(new Hole(9860));
    }

    public String getName() {
        return name;
    }

    public ObservableList<Slot> getContent() {
        return content;
    }

}

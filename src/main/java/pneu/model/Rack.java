package pneu.model;

import java.util.List;

public class Rack {
    private String name;
    private List<Slot> content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Slot> getContent() {
        return content;
    }

    public void setContent(List<Slot> content) {
        this.content = content;
    }
}

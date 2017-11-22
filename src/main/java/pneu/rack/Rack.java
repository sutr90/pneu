package pneu.rack;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pneu.slot.Hole;
import pneu.slot.Slot;

import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class Rack {
    private String name;

    @JsonManagedReference(value = "rackContent")
    private List<Slot> content;

    public Rack(){

    }

    public Rack(String _name) {
        this.name = _name;
        content = new ArrayList<>();
        content.add(new Hole(9860, this));
    }

    public String getName() {
        return name;
    }

    public List<Slot> getContent() {
        return content;
    }

    public void setContent(List<Slot> content) {
        this.content = content;
    }

    public void setName(String name) {
        this.name = name;
    }

}

package pneu.slot;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import pneu.rack.Rack;
import pneu.vo.TireVO;

public class Tire implements Slot {
    private int id;
    @JsonBackReference(value = "rackContent")
    private Rack rack;
    private TireVO tireInfo;

    public Tire(){}

    public Tire(int id, Rack rack, TireVO tireInfo) {
        this.id = id;
        this.rack = rack;
        this.tireInfo = tireInfo;
    }

    @JsonIgnore
    @Override
    public int getWidth() {
        String width = tireInfo.size.split("/")[0].trim();
        return Integer.parseInt(width) * (Integer.parseInt(tireInfo.count) / 2);
    }

    @Override
    public Rack getRack() {
        return rack;
    }

    public int getId() {
        return id;
    }

    public TireVO getTireInfo() {
        return tireInfo;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setRack(Rack rack) {
        this.rack = rack;
    }

    public void setTireInfo(TireVO tireInfo) {
        this.tireInfo = tireInfo;
    }
}

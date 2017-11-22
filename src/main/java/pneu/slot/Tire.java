package pneu.slot;

import pneu.rack.Rack;
import pneu.vo.TireVO;

public class Tire implements Slot {
    private final int id;
    private transient final Rack rack;
    private final TireVO tireInfo;

    public Tire(int id, Rack rack, TireVO tireInfo) {
        this.id = id;
        this.rack = rack;
        this.tireInfo = tireInfo;
    }

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
}

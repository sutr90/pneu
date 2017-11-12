package pneu.slot;

import pneu.rack.Rack;
import pneu.vo.TireVO;

public class Tire implements Slot {
    private final int id;
    private final Rack rack;
    private final TireVO tireInfo;

    public Tire(int id, Rack rack, TireVO tireInfo) {
        this.id = id;
        this.rack = rack;
        this.tireInfo = tireInfo;
    }

    @Override
    public int getWidth() {
        return 300;
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

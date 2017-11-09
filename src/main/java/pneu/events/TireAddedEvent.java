package pneu.events;

import pneu.model.Rack;
import pneu.model.Tire;

public class TireAddedEvent {
    private final Rack rack;
    private final Tire tire;

    public Rack getRack() {
        return rack;
    }

    public Tire getTire() {
        return tire;
    }

    public TireAddedEvent(Rack rack, Tire tire) {
        this.rack = rack;
        this.tire = tire;

    }
}

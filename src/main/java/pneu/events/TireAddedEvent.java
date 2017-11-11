package pneu.events;

import pneu.model.Tire;

public class TireAddedEvent {
    private final Tire tire;

    public Tire getTire() {
        return tire;
    }

    public TireAddedEvent(Tire tire) {
        this.tire = tire;

    }
}

package pneu.events;

import pneu.model.Slot;

public class SlotSelectedEvent {
    public final Slot slot;

    public SlotSelectedEvent(Slot slot) {
        this.slot = slot;
    }
}

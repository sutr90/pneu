package pneu.events;

import pneu.slot.Slot;

public class SlotSelectedEvent {
    public final Slot slot;

    public SlotSelectedEvent(Slot slot) {
        this.slot = slot;
    }
}

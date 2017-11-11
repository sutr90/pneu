package pneu.view;

import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import pneu.model.Slot;
import pneu.model.Tire;

public class SlotView extends Group {
    private final Slot slot;

    public SlotView(Slot slot) {
        this.slot = slot;

        Paint color = Paint.valueOf("green");
        if (slot instanceof Tire) {
            color = Paint.valueOf("red");
        }

        getChildren().add(new Rectangle(slot.getWidth() / 10, 50, color));
    }
}

package pneu.slot;

import com.sun.istack.internal.NotNull;
import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import pneu.events.SlotSelectedEvent;

public class SlotView extends Group {
    private final Rectangle r;
    private final Slot slot;
    private Paint defaultColor;

    public SlotView(@NotNull Slot slot) {
        this.slot = slot;
        setDefaultColor();
        r = new Rectangle(slot.getWidth() / 10, 100);
        r.setOnMouseClicked(event -> EventBus.getDefault().post(new SlotSelectedEvent(slot)));
        r.setFill(defaultColor);
        getChildren().add(r);
        EventBus.getDefault().register(this);
    }

    private void setDefaultColor() {
        Paint color = Paint.valueOf("green");
        if (slot instanceof Tire) {
            color = Paint.valueOf("red");
        }

        defaultColor = color;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSlotSelected(SlotSelectedEvent slotSelectedEvent) {
        if (slotSelectedEvent.slot == slot) {
            r.setFill(Paint.valueOf("yellow"));
        } else {
            r.setFill(defaultColor);
        }
    }
}

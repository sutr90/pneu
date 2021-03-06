package pneu.rack;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import pneu.events.TireAddedEvent;
import pneu.slot.Slot;
import pneu.storage.StorageService;
import pneu.slot.SlotView;

import javax.inject.Inject;
import java.util.List;

public class RackController {
    @FXML
    public Label label;

    @Inject
    private StorageService storageService;

    @Inject
    private String rackName;

    @FXML
    private HBox slots;

    private List<Slot> content;

    @Inject
    public void initialize() {
        label.setText(rackName);
        EventBus.getDefault().register(this);
        content = storageService.getRack(rackName).getContent();
        renderSlots();
    }

    private void renderSlots() {
        slots.getChildren().clear();
        content.forEach(slot -> slots.getChildren().add(new SlotView(slot)));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTireAdded(TireAddedEvent event) {
        if (rackName.equals(event.getTire().getRack().getName())) {
            renderSlots();
        }
    }
}

package pneu.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import pneu.controller.vo.TireVO;
import pneu.events.SlotSelectedEvent;
import pneu.events.TireAddedEvent;
import pneu.model.*;
import pneu.view.RackView;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StorageController {
    @FXML
    public VBox container;

    @Inject
    private StorageLoader loader;

    @Inject
    private StorageService storageService;

    @Inject
    private String persistenceLocation;

    @FXML
    private Button addButton;

    private Slot selectedSlot;

    @Inject
    public void initialize() {
        if (persistenceLocation == null) {
            storageService.setStorage(loader.createEmpty());
        } else {
            storageService.setStorage(loader.loadFromPersistance(persistenceLocation));
        }

        for (final Rack rack : getRacks()) {
            Map<Object, Object> customProperties = new HashMap<>();
            customProperties.put("storageService", storageService);
            customProperties.put("rackName", rack.getName());

            RackView view = new RackView(customProperties::get);
            container.getChildren().add(view.getView());
        }

        addButton.setOnAction(event -> {
            if(selectedSlot != null && selectedSlot instanceof Hole) {
                addTire("A", (Hole) selectedSlot, new TireVO());
            }
        });

        EventBus.getDefault().register(this);
    }

    public void addTire(String rackName, Hole hole, TireVO tireInfo) {
        Tire tire = storageService.storeTire(rackName, hole, tireInfo);
        Rack rack = storageService.getRack(rackName);

        EventBus.getDefault().post(new TireAddedEvent(rack, tire));
        EventBus.getDefault().post(new SlotSelectedEvent(null));
    }


    public void removeTire(Tire tire) {
        storageService.removeTire(tire);
    }

    public List<Rack> getRacks() {
        return storageService.getRacks();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSlotSelected(SlotSelectedEvent slotSelectedEvent) {
        this.selectedSlot = slotSelectedEvent.slot;
    }
}

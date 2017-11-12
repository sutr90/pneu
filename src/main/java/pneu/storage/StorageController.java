package pneu.storage;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import pneu.events.SlotSelectedEvent;
import pneu.events.TireAddedEvent;
import pneu.events.TireFormSubmitted;
import pneu.rack.Rack;
import pneu.rack.RackView;
import pneu.slot.Hole;
import pneu.slot.Slot;
import pneu.slot.Tire;
import pneu.vo.TireVO;

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

        EventBus.getDefault().register(this);
    }

    public Tire addTire(Hole hole, TireVO tireInfo) {
        Tire tire = storageService.storeTire(hole, tireInfo);

        EventBus.getDefault().post(new TireAddedEvent(tire));
        EventBus.getDefault().post(new SlotSelectedEvent(null));
        return tire;
    }


    public Hole removeTire(Tire tire) {
        return storageService.removeTire(tire);
    }

    public List<Rack> getRacks() {
        return storageService.getRacks();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSlotSelected(SlotSelectedEvent slotSelectedEvent) {
        storageService.setSelectedSlot(slotSelectedEvent.slot);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTireFormSubmitted(TireFormSubmitted e) {
        Slot selectedSlot = storageService.getSelectedSlot();
        storageService.getManufacturers().add(e.tireInfo.manufacturer);
        if (selectedSlot != null && selectedSlot instanceof Hole) {
            addTire((Hole) selectedSlot, e.tireInfo);
        } else if (selectedSlot instanceof Tire) {
            Hole hole = removeTire((Tire) selectedSlot);
            Tire newTire = addTire(hole, e.tireInfo);
        }
    }
}

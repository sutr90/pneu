package pneu.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import pneu.controller.vo.TireVO;
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
            addTire("A", (Hole) storageService.getRack("A").getContent().stream().filter(Hole.class::isInstance).findFirst().get(), new TireVO());
            System.out.println("push");
        });
    }

    public void addTire(String rackName, Hole hole, TireVO tireInfo) {
        storageService.storeTire(rackName, hole, tireInfo);
    }


    public void removeTire(Tire tire) {
        storageService.removeTire(tire);
    }

    public List<Rack> getRacks() {
        return storageService.getRacks();
    }
}

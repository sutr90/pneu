package pneu.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import pneu.controller.vo.TireVO;
import pneu.model.*;

import javax.inject.Inject;
import java.util.List;

public class MainController {
    @FXML
    public Pane sidebar;

    @FXML
    public Pane mainPanel;

    @Inject
    private StorageLoader loader;

    @Inject
    private StorageService storageService;

    @Inject
    private String persistenceLocation;

    private Storage storage;

    @Inject
    public void initialize() {
        if (persistenceLocation == null) {
            storage = loader.createEmpty();
        } else {
            storage = loader.loadFromPersistance(persistenceLocation);
        }
    }

    public void addTire(String rackName, Hole hole, TireVO tireInfo) {
        storageService.storeTire(storage, rackName, hole, tireInfo);
    }


    public void removeTire(Tire tire) {
        storageService.removeTire(storage, tire);
    }

    public List<Rack> getRacks() {
        return storage.getRacks();
    }
}

package pneu.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import pneu.controller.vo.TireVO;
import pneu.model.*;
import pneu.view.RackView;

import javax.inject.Inject;
import java.util.List;

public class StorageController {
    @FXML
    public VBox container;

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

        for (final Rack rack : getRacks()) {
            RackView view = new RackView();
            container.getChildren().add(view.getView());
            RackController presenter = (RackController) view.getPresenter();
            presenter.setRack(rack);
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

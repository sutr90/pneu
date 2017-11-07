package pneu.controller;

import pneu.controller.vo.TireVO;
import pneu.model.*;

import javax.inject.Inject;
import java.util.List;

public class PneuController {
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


    public void removeTire() {

    }

    public List<Rack> getRacks() {
        return storage.getRacks();
    }
}

package pneu.controller;

import javafx.fxml.Initializable;
import pneu.model.Rack;
import pneu.model.Slot;
import pneu.model.Storage;
import pneu.model.Tire;

import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;


public class PneuController implements Initializable {
    @Inject
    private Storage storage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void addTire(String rackName) {
        Optional<Rack> rackOptional = storage.getRacks().stream().filter(rack -> rack.getName().equals(rackName)).findFirst();

        if (rackOptional.isPresent()) {
            Rack rackInstance = rackOptional.get();
            List<Slot> content = rackInstance.getContent();
            getFreeSlotId(content);
        } else {
            throw new RuntimeException();
        }
    }

    int getFreeSlotId(List<Slot> content) {
        Set<Integer> ids = content.stream().filter(slot -> slot instanceof Tire).map(value -> ((Tire) value).getId()).collect(Collectors.toSet());

        if (ids.size() == 0) {
            return 0;
        }

        for (int i = 0; i < ids.size(); i++) {
            if(!ids.contains(i)){
                return i;
            }
        }

        return ids.size();
    }

    public void removeTire() {

    }
}

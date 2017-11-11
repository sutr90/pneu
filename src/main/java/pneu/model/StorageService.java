package pneu.model;

import pneu.controller.vo.TireVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class StorageService {

    private Storage storage;

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Tire storeTire(String rackName, Hole hole, TireVO tireInfo) {
        Optional<Rack> rackOptional = storage.getRacks().stream().filter(rack -> rack.getName().equals(rackName)).findFirst();

        if (rackOptional.isPresent()) {
            Rack rackInstance = rackOptional.get();
            List<Slot> content = rackInstance.getContent();
            int tireId = getFreeSlotId(content);

            int holeIndex = content.indexOf(hole);
            if (holeIndex == -1) {
                return null;
            }

            content.remove(holeIndex);

            // todo check if fits

            Tire tire = new Tire(tireId);
            content.add(holeIndex, new Hole(hole.getWidth()-tire.getWidth()));
            content.add(holeIndex, tire);
            return tire;
        }
        return null;
    }

    private int getFreeSlotId(List<Slot> content) {
        Set<Integer> ids = content.stream().filter(slot -> slot instanceof Tire).map(value -> ((Tire) value).getId()).collect(Collectors.toSet());

        if (ids.size() == 0) {
            return 0;
        }

        for (int i = 0; i < ids.size(); i++) {
            if (!ids.contains(i)) {
                return i;
            }
        }

        return ids.size();
    }

    public void removeTire(Tire tire) {
        List<Slot> contents = null;
        for (Rack r : storage.getRacks()) {
            if (r.getContent().contains(tire)) {
                contents = r.getContent();
                break;
            }
        }

        if (contents == null) {
            throw new RuntimeException();
        }

        int idx = contents.indexOf(tire);

        contents.remove(idx);
        contents.add(idx, new Hole(tire.getWidth()));

        mergeHoles(contents);
    }

    private void mergeHoles(List<Slot> contents) {
        List<Slot> tempList = new ArrayList<>();

        int lenBuffer = 0;
        for (Slot slot : contents) {
            if (slot.getClass().equals(Tire.class)) {
                if (lenBuffer > 0) {
                    tempList.add(new Hole(lenBuffer));
                    lenBuffer = 0;
                }
                tempList.add(slot);
            } else {
                lenBuffer += slot.getWidth();
            }
        }

        if (lenBuffer > 0) {
            tempList.add(new Hole(lenBuffer));
        }

        contents.clear();
        contents.addAll(tempList);
    }

    public Rack getRack(String rackName) {
        return storage.getRacks().stream().filter(rack -> rackName.equals(rack.getName())).findFirst().get();
    }

    public List<Rack> getRacks() {
        return storage.getRacks();
    }
}

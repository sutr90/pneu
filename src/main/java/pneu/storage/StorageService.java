package pneu.storage;

import pneu.vo.TireVO;
import pneu.rack.Rack;
import pneu.slot.Hole;
import pneu.slot.Slot;
import pneu.slot.Tire;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StorageService {

    private Storage storage;

    private Slot selectedSlot;
    private List<String> manufacturers;

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Tire storeTire(Hole hole, TireVO tireInfo) {
        Rack rack = hole.getRack();
        List<Slot> content = rack.getContent();
        int tireId = getFreeSlotId(content);

        int holeIndex = content.indexOf(hole);
        if (holeIndex == -1) {
            return null;
        }

        content.remove(holeIndex);

        // todo check if fits

        Tire tire = new Tire(tireId, rack, tireInfo);
        content.add(holeIndex, new Hole(hole.getWidth() - tire.getWidth(), rack));
        content.add(holeIndex, tire);
        return tire;
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
        List<Slot> contents = tire.getRack().getContent();

        int idx = contents.indexOf(tire);

        contents.remove(idx);
        contents.add(idx, new Hole(tire.getWidth(), tire.getRack()));

        mergeHoles(tire.getRack());
    }

    private void mergeHoles(Rack rack) {
        List<Slot> tempList = new ArrayList<>();

        List<Slot> contents = rack.getContent();
        int lenBuffer = 0;
        for (Slot slot : contents) {
            if (slot.getClass().equals(Tire.class)) {
                if (lenBuffer > 0) {
                    tempList.add(new Hole(lenBuffer, rack));
                    lenBuffer = 0;
                }
                tempList.add(slot);
            } else {
                lenBuffer += slot.getWidth();
            }
        }

        if (lenBuffer > 0) {
            tempList.add(new Hole(lenBuffer, rack));
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

    public Slot getSelectedSlot() {
        return selectedSlot;
    }

    public void setSelectedSlot(Slot selectedSlot) {
        this.selectedSlot = selectedSlot;
    }

    public List<String> getManufacturers() {
        return Arrays.asList("Option 1", "Option 2");
    }

    void setManufacturers(List<String> manufacturers) {
        this.manufacturers = manufacturers;
    }
}

package pneu.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import pneu.rack.Rack;
import pneu.slot.Hole;
import pneu.slot.Slot;
import pneu.slot.Tire;
import pneu.vo.TireVO;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StorageService {

    private Storage storage;

    private Slot selectedSlot;

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

    public Hole removeTire(Tire tire) {
        Rack rack = tire.getRack();
        List<Slot> contents = rack.getContent();

        int idx = contents.indexOf(tire);

        contents.remove(idx);
        contents.add(idx, new Hole(tire.getWidth(), rack));

        mergeHoles(rack);

        return findCreatedHole(rack, idx);
    }

    private Hole findCreatedHole(Rack rack, int idx) {
        List<Slot> contents = rack.getContent();
        if (contents.size() - 1 < idx) {
            idx = contents.size() - 1;
        }
        for (int i = idx; i >= 0; i--) {
            if (contents.get(i) instanceof Hole) {
                return (Hole) contents.get(i);
            }
        }

        return null;
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

    public Set<String> getManufacturers() {
        return storage.getManufacturers();
    }

    public void saveData() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        try {
            objectMapper.writeValue(new File("D:\\tmp\\pneuSave.json"), storage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        try {
            byte[] bytes = Files.readAllBytes(Paths.get("D:\\tmp\\pneuSave.json"));
            String json = new String(bytes, "UTF-8");
            Storage store = objectMapper.readValue(json, Storage.class);

            store.getRacks();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

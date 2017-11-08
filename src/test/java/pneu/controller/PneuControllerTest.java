package pneu.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import pneu.controller.vo.TireVO;
import pneu.model.*;

import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class PneuControllerTest {
    @Spy
    private StorageService storageService;

    @Spy
    private StorageLoader loader;

    @InjectMocks
    private PneuController controller;

    @Before
    public void init() {
        controller = new PneuController();
        MockitoAnnotations.initMocks(this);
        controller.initialize();
    }

    @Test
    public void emptyStorage() {
        Assert.assertEquals(5, controller.getRacks().size());
    }

    @Test
    public void getNameRack() {
        Optional<Rack> aRack = controller.getRacks().stream().filter(rack -> rack.getName().equals("A")).findFirst();
        Assert.assertEquals(true, aRack.isPresent());
    }

    @Test
    public void addTireToEmptyRack() {
        Rack aRack = controller.getRacks().stream().filter(rack -> rack.getName().equals("A")).findFirst().get();

        Assert.assertEquals(1, aRack.getContent().size());
        Assert.assertEquals(Hole.class, aRack.getContent().get(0).getClass());

        controller.addTire("A", (Hole) aRack.getContent().get(0), new TireVO());
        Assert.assertEquals(2, aRack.getContent().size());
        Assert.assertEquals(Tire.class, aRack.getContent().get(0).getClass());
        Assert.assertEquals(Hole.class, aRack.getContent().get(1).getClass());

        Tire firstTire = (Tire) aRack.getContent().get(0);
        Assert.assertEquals(0, firstTire.getId());
    }

    @Test
    public void addNewTireToRack() {
        Rack aRack = controller.getRacks().stream().filter(rack -> rack.getName().equals("A")).findFirst().get();

        List<Slot> content = aRack.getContent();
        Assert.assertEquals(1, content.size());
        Assert.assertEquals(Hole.class, content.get(0).getClass());

        controller.addTire("A", (Hole) content.get(0), new TireVO());
        controller.addTire("A", (Hole) content.get(1), new TireVO());

        Assert.assertEquals(3, content.size());
        Assert.assertEquals(Tire.class, content.get(0).getClass());
        Assert.assertEquals(0, ((Tire) content.get(0)).getId());
        Assert.assertEquals(Tire.class, content.get(1).getClass());
        Assert.assertEquals(1, ((Tire) content.get(1)).getId());
        Assert.assertEquals(Hole.class, content.get(2).getClass());

        Tire firstTire = (Tire) content.get(0);
        Assert.assertEquals(0, firstTire.getId());
    }

    @Test
    public void removeFirstTire() {
        Rack aRack = controller.getRacks().stream().filter(rack -> rack.getName().equals("A")).findFirst().get();

        List<Slot> content = aRack.getContent();
        controller.addTire("A", (Hole) content.get(0), new TireVO());
        controller.addTire("A", (Hole) content.get(1), new TireVO());

        Tire firstTire = (Tire) content.get(0);

        Assert.assertEquals(3, content.size());
        Assert.assertEquals(Tire.class, content.get(0).getClass());

        controller.removeTire(firstTire);
        Assert.assertEquals(3, content.size());
        Assert.assertEquals(Hole.class, content.get(0).getClass());
        Assert.assertEquals(Tire.class, content.get(1).getClass());
        Assert.assertEquals(Hole.class, content.get(2).getClass());
    }

    @Test
    public void removeSecondTire() {
        Rack aRack = controller.getRacks().stream().filter(rack -> rack.getName().equals("A")).findFirst().get();

        List<Slot> content = aRack.getContent();
        controller.addTire("A", (Hole) content.get(0), new TireVO());
        controller.addTire("A", (Hole) content.get(1), new TireVO());

        Tire secondTire = (Tire) content.get(1);

        Assert.assertEquals(3, content.size());
        Assert.assertEquals(Tire.class, content.get(0).getClass());

        controller.removeTire(secondTire);
        Assert.assertEquals(2, content.size());
        Assert.assertEquals(Tire.class, content.get(0).getClass());
        Assert.assertEquals(Hole.class, content.get(1).getClass());
    }
}
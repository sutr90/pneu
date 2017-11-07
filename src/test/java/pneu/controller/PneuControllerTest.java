package pneu.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import pneu.controller.vo.TireVO;
import pneu.model.*;

import javax.inject.Inject;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

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
    public void addTireToEmptyRack(){
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
    public void addNewTireToRack(){
        Rack aRack = controller.getRacks().stream().filter(rack -> rack.getName().equals("A")).findFirst().get();

        Assert.assertEquals(1, aRack.getContent().size());
        Assert.assertEquals(Hole.class, aRack.getContent().get(0).getClass());

        controller.addTire("A", (Hole) aRack.getContent().get(0), new TireVO());
        controller.addTire("A", (Hole) aRack.getContent().get(0), new TireVO());
        Assert.assertEquals(2, aRack.getContent().size());
        Assert.assertEquals(Tire.class, aRack.getContent().get(0).getClass());
        Assert.assertEquals(Hole.class, aRack.getContent().get(1).getClass());

        Tire firstTire = (Tire) aRack.getContent().get(0);
        Assert.assertEquals(0, firstTire.getId());
    }
}
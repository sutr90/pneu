package pneu.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pneu.model.Hole;
import pneu.model.Slot;
import pneu.model.Tire;

import java.util.ArrayList;
import java.util.List;

public class PneuControllerTest {
    private PneuController controller;

    @Before
    public void init(){
        this.controller = new PneuController();
    }

    @Test
    public void emptyRack(){
        List<Slot> slots = new ArrayList<>();
        slots.add(new Hole());

        Assert.assertEquals(0, controller.getFreeSlotId(slots));
    }

    @Test
    public void singleTireAtBeginning(){
        List<Slot> slots = new ArrayList<>();
        slots.add(new Tire(0));
        slots.add(new Hole());

        Assert.assertEquals(1, controller.getFreeSlotId(slots));
    }

    @Test
    public void singleTireAtEnd(){
        List<Slot> slots = new ArrayList<>();
        slots.add(new Hole());
        slots.add(new Tire(1));

        Assert.assertEquals(0, controller.getFreeSlotId(slots));
    }

    @Test
    public void twoHoleAtBeginning(){
        List<Slot> slots = new ArrayList<>();
        slots.add(new Hole());
        slots.add(new Tire(2));

        Assert.assertEquals(0, controller.getFreeSlotId(slots));
    }


    @Test
    public void singleHoleAtBeginningMultipleTires(){
        List<Slot> slots = new ArrayList<>();
        slots.add(new Hole());
        slots.add(new Tire(1));
        slots.add(new Tire(2));
        slots.add(new Tire(3));

        Assert.assertEquals(0, controller.getFreeSlotId(slots));
    }

    @Test
    public void twoHoleAtBeginningMultipleTires(){
        List<Slot> slots = new ArrayList<>();
        slots.add(new Hole());
        slots.add(new Tire(2));
        slots.add(new Tire(3));

        Assert.assertEquals(0, controller.getFreeSlotId(slots));
    }

    @Test
    public void singleHoleInMiddle(){
        List<Slot> slots = new ArrayList<>();
        slots.add(new Tire(0));
        slots.add(new Hole());
        slots.add(new Tire(2));

        Assert.assertEquals(1, controller.getFreeSlotId(slots));
    }

    @Test
    public void twoHoleInMiddle(){
        List<Slot> slots = new ArrayList<>();
        slots.add(new Tire(0));
        slots.add(new Hole());
        slots.add(new Tire(3));

        Assert.assertEquals(1, controller.getFreeSlotId(slots));
    }

}
package pneu.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pneu.model.Rack;
import pneu.model.StorageService;

import javax.inject.Inject;

public class RackController {
    @FXML
    public Label label;

    @Inject
    private StorageService storageService;

    private Rack rack;

    public void setRack(Rack rack) {
        this.rack = rack;
        label.setText(rack.getName());
    }
}

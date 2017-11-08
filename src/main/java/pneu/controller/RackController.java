package pneu.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import pneu.model.StorageService;

import javax.inject.Inject;

public class RackController {
    @FXML
    public Label label;

    @Inject
    private StorageService storageService;

    @Inject
    private String rackName;

    @Inject
    public void initialize() {
        label.setText(rackName);
        System.out.println(storageService);
    }
}

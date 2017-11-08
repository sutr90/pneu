package pneu.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import pneu.view.StorageView;

import javax.inject.Inject;

public class MainController {
    @FXML
    public Pane sidebar;

    @FXML
    public Pane mainPanel;

    @Inject
    public void initialize() {
        StorageView view = new StorageView();
        mainPanel.getChildren().add(view.getView());
    }
}

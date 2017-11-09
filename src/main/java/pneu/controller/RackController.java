package pneu.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.shape.Rectangle;
import pneu.model.Slot;
import pneu.model.StorageService;

import javax.inject.Inject;

public class RackController {
    @FXML
    public Label label;

    @Inject
    private StorageService storageService;

    @Inject
    private String rackName;

    @FXML
    private ListView<Slot> slots;

    @Inject
    public void initialize() {
        label.setText(rackName);
        System.out.println(storageService);

        ObservableList<Slot> myObservableList = storageService.getRack(rackName).getContent();
        slots.setItems(myObservableList);

        slots.setCellFactory((ListView<Slot> l) -> new ColorRectCell());
    }

    static class ColorRectCell extends ListCell<Slot> {
        @Override
        public void updateItem(Slot item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                Rectangle rect = new Rectangle(30 + item.getWidth() / 100, 20);
                setGraphic(rect);
            }
        }
    }
}

package pneu.main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.greenrobot.eventbus.EventBus;
import pneu.events.AddButtonPressedEvent;
import pneu.storage.StorageView;

import javax.inject.Inject;

public class MainController {
    @FXML
    public Pane menuBar;

    @FXML
    public Pane mainPanel;

    @FXML
    private Button addButton;

    @Inject
    public void initialize() {
        StorageView view = new StorageView();
        mainPanel.getChildren().add(view.getView());

        addButton.setOnAction(event -> EventBus.getDefault().post(new AddButtonPressedEvent()));
    }
}


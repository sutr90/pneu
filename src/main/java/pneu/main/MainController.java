package pneu.main;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import pneu.events.AddButtonPressedEvent;
import pneu.storage.StorageService;
import pneu.storage.StorageView;
import pneu.tireForm.TireFormView;

import javax.inject.Inject;

public class MainController {
    @FXML
    public Pane menuBar;

    @FXML
    public Pane mainPanel;

    @FXML
    private Button addButton;

    @Inject
    private StorageService storageService;

    @Inject
    public void initialize() {
        StorageView view = new StorageView();
        mainPanel.getChildren().add(view.getView());

        addButton.setOnAction(event -> {
            TireFormView tfv = new TireFormView();
            Stage tireForm = new Stage();
            Scene scene = new Scene(tfv.getView());
            tireForm.setScene(scene);
            tireForm.setResizable(false);
            tireForm .initOwner(addButton.getScene().getWindow());
            tireForm .initModality(Modality.APPLICATION_MODAL);
            tireForm .showAndWait();

            EventBus.getDefault().post(new AddButtonPressedEvent());
        });

        storageService.getRacks();
    }
}


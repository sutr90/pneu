package pneu.main;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
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


        storageService.getRacks();
    }

    @FXML
    public void showDialog() {
        TireFormView tfv = new TireFormView();
        Stage tireForm = new Stage();
        Scene scene = new Scene(tfv.getView());
        scene.getStylesheets().add(getClass().getResource("../app.css").toExternalForm());
        tireForm.setScene(scene);
        tireForm.setResizable(false);
        tireForm.initOwner(addButton.getScene().getWindow());
        tireForm.initModality(Modality.APPLICATION_MODAL);
        tireForm.showAndWait();
    }
}


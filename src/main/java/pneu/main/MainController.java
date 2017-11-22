package pneu.main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import pneu.events.ShowTireFormEvent;
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
        EventBus.getDefault().register(this);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowTireFormEvent(ShowTireFormEvent e){
        showDialog();
    }

    public void saveData(ActionEvent actionEvent) {
        storageService.saveData();
    }
}


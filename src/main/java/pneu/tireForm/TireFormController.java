package pneu.tireForm;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.controlsfx.control.SegmentedButton;
import pneu.storage.StorageService;

import javax.inject.Inject;

public class TireFormController {
    @FXML
    public Button cancelButton;
    public Button okButton;
    public TextField size;
    public TextField radius;
    public TextField count;
    public TextField frontRight;
    public TextField frontLeft;
    public TextField rearLeft;
    public TextField rearRight;
    public TextField price;
    public SegmentedButton tireSegButton;
    public SegmentedButton rimSegButton;
    public Button contractButton;
    public Button returnButton;
    public DatePicker dateFrom;
    public DatePicker dateTo;
    public TextField name;
    public TextField surname;
    public TextField city;
    public TextField street;
    public TextField licensePlate;
    @Inject
    private StorageService storageService;

    @Inject
    public void initialize() {
        //todo prefill info based on storageService status
    }
}

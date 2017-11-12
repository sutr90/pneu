package pneu.tireForm;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.controlsfx.control.SegmentedButton;
import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import pneu.storage.StorageService;
import pneu.utils.Utils;

import javax.inject.Inject;

public class TireFormController {
    @FXML
    public Button cancelButton;
    @FXML
    public Button okButton;
    @FXML
    public TextField size;
    @FXML
    public TextField radius;
    @FXML
    public TextField count;
    @FXML
    public TextField frontRight;
    @FXML
    public TextField frontLeft;
    @FXML
    public TextField rearLeft;
    @FXML
    public TextField rearRight;
    @FXML
    public TextField price;
    @FXML
    public SegmentedButton tireSegButton;
    @FXML
    public SegmentedButton rimSegButton;
    @FXML
    public Button contractButton;
    @FXML
    public Button returnButton;
    @FXML
    public DatePicker dateFrom;
    @FXML
    public DatePicker dateTo;
    @FXML
    public TextField name;
    @FXML
    public TextField surname;
    @FXML
    public TextField city;
    @FXML
    public TextField street;
    @FXML
    public TextField licensePlate;

    @Inject
    private StorageService storageService;

    @Inject
    public void initialize() {
        //todo prefill info based on storageService status

        ValidationSupport support = new ValidationSupport();

        Validator<String> numericOrEmpty = (control, value) -> {
            boolean condition = false;

            if (value != null && value.trim().length() > 0) {
                condition = !Utils.isNumeric(value);
            }

            System.out.println(value + condition);

            return ValidationResult.fromMessageIf(control, "Zadejte číslo", Severity.ERROR, condition);
        };

        support.registerValidator(radius, true, numericOrEmpty);
    }
}

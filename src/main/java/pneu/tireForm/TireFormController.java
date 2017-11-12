package pneu.tireForm;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.SegmentedButton;
import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.validation.ValidationSupport;
import pneu.storage.StorageService;

import javax.inject.Inject;
import java.time.LocalDate;

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
    @FXML
    public TextField manufacturer;

    @Inject
    private StorageService storageService;

    static class PersistentButtonToggleGroup extends ToggleGroup {
        public PersistentButtonToggleGroup() {
            super();
            getToggles().addListener((ListChangeListener<Toggle>) c -> {
                while (c.next()) for (final Toggle addedToggle : c.getAddedSubList())
                    ((ToggleButton) addedToggle).addEventFilter(MouseEvent.MOUSE_RELEASED, mouseEvent -> {
                        if (addedToggle.equals(getSelectedToggle())) mouseEvent.consume();
                    });
            });
        }
    }

    @Inject
    public void initialize() {
        //todo prefill info based on storageService status

        ValidationSupport support = new ValidationSupport();
        support.registerValidator(radius, true, Validators.emptyNumericError);
        support.registerValidator(price, false, Validators.emptyNumericError);
        support.registerValidator(count, true, Validators.emptyNumericError);
        support.registerValidator(size, true, Validators.tireSizeFormatError);
        support.registerValidator(dateFrom, false, Validators.emptyDateWarn);
        support.registerValidator(dateTo, false, Validators.emptyDateWarn);
        support.registerValidator(name, false, Validators.emptyStringWarn);
        support.registerValidator(surname, false, Validators.emptyStringWarn);
        support.initInitialDecoration();

        tireSegButton.getStyleClass().add(SegmentedButton.STYLE_CLASS_DARK);
        tireSegButton.setToggleGroup(new PersistentButtonToggleGroup());
        rimSegButton.getStyleClass().add(SegmentedButton.STYLE_CLASS_DARK);
        rimSegButton.setToggleGroup(new PersistentButtonToggleGroup());

        if (storageService.getSelectedSlot() == null) {
            initDefaults();
        }

        TextFields.bindAutoCompletion(manufacturer, storageService.getManufacturers());
    }

    private void initDefaults() {
        count.setText("4");
        price.setText("200");

        tireSegButton.getButtons().get(0).setSelected(true);
        rimSegButton.getButtons().get(0).setSelected(true);
        dateFrom.setValue(LocalDate.now());
    }
}

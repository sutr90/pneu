package pneu.tireForm;

import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.SegmentedButton;
import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.validation.ValidationSupport;
import pneu.storage.StorageService;
import pneu.vo.*;

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

    @FXML
    public void onCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onSubmit() {
        Stage stage = (Stage) okButton.getScene().getWindow();
        stage.close();
    }


    static class PersistentButtonToggleGroup extends ToggleGroup {
        PersistentButtonToggleGroup() {
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

        okButton.setDefaultButton(true);
        cancelButton.setCancelButton(true);

        okButton.disableProperty().bind(support.invalidProperty());
    }

    private void initDefaults() {
        count.setText("4");
        price.setText("200");

        tireSegButton.getButtons().get(0).setSelected(true);
        rimSegButton.getButtons().get(0).setSelected(true);
        dateFrom.setValue(LocalDate.now());
    }

    public TireVO convertToVO() {
        TireVO tire = new TireVO();
        CustomerVO customer = new CustomerVO();
        OrderVO order = new OrderVO();

        customer.name = name.getText();
        customer.surname = surname.getText();
        customer.street = street.getText();
        customer.city = city.getText();
        customer.licensePlate = licensePlate.getText();

        order.dateFrom = dateFrom.getValue();
        order.dateTo = dateTo.getValue();
        order.price = price.getText();

        tire.manufacturer = manufacturer.getText();
        tire.rimType = getRimType();
        tire.tireType = getTireType();
        tire.rearLeft = rearLeft.getText();
        tire.rearRight = rearRight.getText();
        tire.frontLeft = frontLeft.getText();
        tire.frontRight = frontRight.getText();
        tire.count = count.getText();
        tire.radius = radius.getText();
        tire.size = size.getText();

        tire.order = order;
        tire.customer = customer;

        return tire;
    }

    private TireType getTireType() {
        ToggleButton selected = tireSegButton.getButtons().stream().filter(ToggleButton::isSelected).findFirst().get();
        switch (selected.getText()) {
            case "Letní":
                return TireType.SUMMER;
            case "Zimní":
                return TireType.WINTER;
            default:
                return TireType.UNIVERSAL;
        }
    }

    private RimType getRimType() {
        ToggleButton selected = rimSegButton.getButtons().stream().filter(ToggleButton::isSelected).findFirst().get();
        switch (selected.getText()) {
            case "Ocel":
                return RimType.STEEL;
            case "Hliník":
                return RimType.ALUMINUM;
            default:
                return RimType.NONE;
        }
    }
}

package pneu.tireForm;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.controlsfx.control.SegmentedButton;
import org.controlsfx.control.textfield.TextFields;
import org.controlsfx.validation.ValidationSupport;
import pneu.slot.Hole;
import pneu.slot.Slot;
import pneu.slot.Tire;
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
    @FXML
    public GridPane root;
    @Inject
    private StorageService storageService;

    private boolean dirty;

    private boolean creating;

    @FXML
    public void onCancel() {
        if (dirty) {
            ButtonType ano = new ButtonType("Ano", ButtonBar.ButtonData.YES);
            ButtonType ne = new ButtonType("Ne", ButtonBar.ButtonData.NO);
            Alert alert = new Alert(Alert.AlertType.WARNING, "Máte neuložené změny. Opravdu chcete zavřít tento dialog?", ano, ne);
            alert.getDialogPane().getStylesheets().add(getClass().getResource("../app.css").toExternalForm());
            alert.setTitle("");
            alert.setHeaderText("Upozornění");
            alert.showAndWait()
                    .filter(response -> response == ano)
                    .ifPresent(response -> closeWindow());
        } else {
            closeWindow();
        }
    }

    @FXML
    public void onSubmit() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) root.getScene().getWindow();
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
        dirty = false;
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

        Slot selectedSlot = storageService.getSelectedSlot();
        if (selectedSlot == null || selectedSlot instanceof Hole) {
            initDefaults();
            creating = true;
        } else {
            fillInit((Tire) selectedSlot);
        }

        TextFields.bindAutoCompletion(manufacturer, storageService.getManufacturers());

        okButton.setDefaultButton(true);
        cancelButton.setCancelButton(true);

        okButton.disableProperty().bind(support.invalidProperty());

        root.getChildren().stream().filter(TextField.class::isInstance)
                .forEach(node -> ((TextField) node).textProperty()
                        .addListener((observable, oldValue, newValue) -> dirty = true));
        root.getChildren().stream().filter(DatePicker.class::isInstance)
                .forEach(node -> ((DatePicker) node).valueProperty()
                        .addListener((observable, oldValue, newValue) -> dirty = true));
        root.getChildren().stream().filter(SegmentedButton.class::isInstance)
                .forEach(node -> ((SegmentedButton) node).getButtons()
                        .forEach(toggleButton -> toggleButton.selectedProperty()
                                .addListener((observable, oldValue, newValue) -> dirty = true)));
    }

    private void fillInit(Tire selectedTire) {
        TireVO tire = selectedTire.getTireInfo();
        CustomerVO customer = tire.customer;
        OrderVO order = tire.order;

        name.setText(customer.name);
        surname.setText(customer.surname);
        street.setText(customer.street);
        city.setText(customer.city);
        licensePlate.setText(customer.licensePlate);

        dateFrom.setValue(order.dateFrom);
        dateTo.setValue(order.dateTo);
        price.setText(order.price);

        manufacturer.setText(tire.manufacturer);
        setRimType(tire.rimType);
        setTireType(tire.tireType);
        rearLeft.setText(tire.rearLeft);
        rearRight.setText(tire.rearRight);
        frontLeft.setText(tire.frontLeft);
        frontRight.setText(tire.frontRight);
        count.setText(tire.count);
        radius.setText(tire.radius);
        size.setText(tire.size);
        dirty = false;
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
        return TireType.valueOf(selected.getText());
    }

    private RimType getRimType() {
        ToggleButton selected = rimSegButton.getButtons().stream().filter(ToggleButton::isSelected).findFirst().get();
        return RimType.valueOf(selected.getText());
    }

    private void setRimType(RimType rimType) {
        rimSegButton.getButtons().stream().filter(toggleButton -> rimType.equalsName(toggleButton.getText())).findFirst().get().setSelected(true);
    }

    private void setTireType(TireType tireType) {
        tireSegButton.getButtons().stream().filter(toggleButton -> tireType.equalsName(toggleButton.getText())).findFirst().get().setSelected(true);
    }
}

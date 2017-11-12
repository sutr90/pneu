package pneu.tireForm;

import org.controlsfx.validation.Severity;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.Validator;
import pneu.utils.Utils;

public class Validators {
    private static Validator<String> isNumeric = (control, value) -> {
        boolean condition = false;
        if (value != null && value.trim().length() > 0) {
            condition = !Utils.isNumeric(value);
        }
        return ValidationResult.fromMessageIf(control, "Zadejte číslo", Severity.ERROR, condition);
    };

    private static Validator<String> sizeFormat = (control, value) -> {
        boolean condition = false;
        if (value != null) {
            condition = !value.matches("\\s*\\d{3,4}\\s*/\\s*\\d{2,4}\\s*");
        }
        return ValidationResult.fromMessageIf(control, "Chybný formát velikosti pneumatik!", Severity.ERROR, condition);
    };

    public static Validator<String> emptyNumericError = Validator.combine(isNumeric, Validator.createEmptyValidator("Zadejte hodnotu", Severity.ERROR));
    public static Validator<Object> emptyDateWarn = Validator.createEmptyValidator("Chybí datum!", Severity.WARNING);
    public static Validator<String> emptyStringWarn = Validator.createEmptyValidator("Chybí hodnota!", Severity.WARNING);
    public static Validator<String> tireSizeFormatError = sizeFormat;


}

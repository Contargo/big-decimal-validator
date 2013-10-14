package de.synyx.validator.bigdecimal;

import java.math.BigDecimal;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * BigDecimal Validator to validate a BigDecimal by his maximum and minimum precision and maxFractionalPlaces. Because
 * the annotation {@link javax.validation.constraints.Digits} can only take longs and no floatingnumbers.
 *
 * <p>Use this Validator with the supported annotation {@link BigDecimalValidate}</p>
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @author  Oliver Messner - messner@synyx.de
 */
public final class BigDecimalConstraintValidator implements ConstraintValidator<BigDecimalValidate, Object> {

    private long maxDecimalPlaces;
    private long minDecimalPlaces;
    private long maxFractionalPlaces;
    private Double minValue;
    private Double maxValue;

    private BigDecimalValidator bigDecimalValidator;

    @Override
    public void initialize(final BigDecimalValidate bigDecimalValidate) {

        maxDecimalPlaces = bigDecimalValidate.maxDecimalPlaces();
        minDecimalPlaces = bigDecimalValidate.minDecimalPlaces();
        maxFractionalPlaces = bigDecimalValidate.maxFractionalPlaces();
        minValue = bigDecimalValidate.minValue();
        maxValue = bigDecimalValidate.maxValue();

        bigDecimalValidator = new BigDecimalValidator();
    }


    @Override
    public boolean isValid(final Object object, final ConstraintValidatorContext constraintValidatorContext) {

        if (object == null) {
            // default validator will check that
            return true;
        } else if (object instanceof BigDecimal) {
            BigDecimal bigDecimal = new BigDecimal(object.toString());

            BigDecimalValidationRules bigDecimalValidationRules = new BigDecimalValidationRules.Builder()
                .maxDecimalPlaces(maxDecimalPlaces).minDecimalPlaces(minDecimalPlaces).maxFractionalPlaces(
                    maxFractionalPlaces).minValue(minValue.toString()).maxValue(maxValue.toString()).build();

            BigDecimalValidationResult result = bigDecimalValidator.validate(bigDecimal, bigDecimalValidationRules);

            if (!result.isValid()) {
                addMessage(constraintValidatorContext, result.getFailMessage());

                return false;
            }
        } else {
            return false;
        }

        return true;
    }


    public void setBigDecimalValidator(BigDecimalValidator bigDecimalValidator) {

        this.bigDecimalValidator = bigDecimalValidator;
    }


    private void addMessage(ConstraintValidatorContext constraintValidatorContext, String message) {

        if (constraintValidatorContext != null) {
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        }
    }
}

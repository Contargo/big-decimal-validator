package net.contargo.validation.bigdecimal;

import java.math.BigDecimal;
import java.math.BigInteger;

import static java.lang.Math.abs;


/**
 * <p>Validates a BigDecimal by minValue, maxValue value and the count of the fraction and decimal places.</p>
 *
 * <p>This validator provides various numbers of validation methods to validate a
 * {@link java.math.BigDecimal BigDecimal} by the given
 * {@link net.contargo.validation.bigdecimal.BigDecimalValidationRules BigDecimalValidationRules}.</p>
 *
 * <pre>
 Example:

 BigDecimal bigDecimal = new BigDecimal("124.2");
 BigDecimalValidationRules bigDecimalValidationRules = new BigDecimalValidationRules.Builder().minDecimalPlaces(1)
    .maxDecimalPlaces(3).macFractionalPlaces(2).minValue("0").maxValue(150).build();

 BigDecimalValidationResult result = bigDecimalValidator.validate(bigDecimal, bigDecimalValidationRules);

 ...
 }
 * </pre>
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class BigDecimalValidator {

    private final boolean checkFractions;

    /**
     * BigDecimalValidator instance which allows fractionals by default.
     */
    public BigDecimalValidator() {

        this(true);
    }


    /**
     * BigDecimalValidator instance with or without fractional checks.
     *
     * @param  checkFractions  <code>true</code> if fractional checks are enabled
     */
    public BigDecimalValidator(boolean checkFractions) {

        this.checkFractions = checkFractions;
    }

    /**
     * Validates a BigDecimal by the given validation rules..
     *
     * @param  bigDecimal  object to test
     *
     * @return  {@link net.contargo.validation.bigdecimal.BigDecimalValidationResult BigDecimalValidationResult} object
     *          with failure message
     */
    public BigDecimalValidationResult validate(BigDecimal bigDecimal,
        BigDecimalValidationRules bigDecimalValidationRules) {

        BigDecimalValidationResult result = new BigDecimalValidationResult();
        BigDecimal thisBigDecimal = parse(bigDecimal, result);

        // return result after parsing errors
        if (!result.isValid()) {
            return result;
        }

        // calculate number if BigDecimal is set by power of x (e.g.: 1E88)
        if (thisBigDecimal.scale() < 0) {
            thisBigDecimal = new BigDecimal(thisBigDecimal.unscaledValue().multiply(
                        BigInteger.TEN.pow(abs(thisBigDecimal.scale()))));
        }

        if (isDecimalOutOfRange(thisBigDecimal, bigDecimalValidationRules, result)) {
            return result;
        }

        if (checkFractions && isFractionalOutOfRange(thisBigDecimal, bigDecimalValidationRules, result)) {
            return result;
        }

        if (isTooBig(thisBigDecimal, bigDecimalValidationRules, result)) {
            return result;
        }

        if (isTooSmall(thisBigDecimal, bigDecimalValidationRules, result)) {
            return result;
        }

        return result;
    }


    /**
     * Parse the <code>BigDecimal</code> with the given options.
     *
     * @param  bigDecimal  <code>BigDecimal</code> to parse.
     *
     * @return  parsed <code>BigDecimal</code>
     */
    private BigDecimal parse(BigDecimal bigDecimal, BigDecimalValidationResult result) {

        BigDecimal thisBigDecimal = bigDecimal;

        if (thisBigDecimal == null) {
            result.setFailMessage("Cannot parse null value.");
        } else {
            if (!checkFractions) {
                thisBigDecimal = new BigDecimal(bigDecimal.intValue());
            }
        }

        return thisBigDecimal;
    }


    /**
     * Checks for range of decimal.
     *
     * @param  bigDecimal  object to test
     * @param  validationRules  keeps the validation rules
     *
     * @return  true if is is out of range, else otherwise
     */
    private boolean isDecimalOutOfRange(BigDecimal bigDecimal, BigDecimalValidationRules validationRules,
        BigDecimalValidationResult result) {

        int actualScale = bigDecimal.scale();
        int actualPrecision = bigDecimal.precision();

        if (actualPrecision <= actualScale) {
            actualPrecision = actualScale + 1;
        }

        int actualDecimalPlaces = actualPrecision - actualScale;

        if (actualDecimalPlaces < validationRules.getMinDecimalPlaces()
                || actualDecimalPlaces > validationRules.getMaxDecimalPlaces()) {
            result.setFailMessage("The count of the digits before the point is out of range. It should be in the range "
                + validationRules.getMinDecimalPlaces() + " - " + validationRules.getMaxDecimalPlaces()
                + " but is " + actualDecimalPlaces + ".");

            return true;
        }

        return false;
    }


    /**
     * Checks for range of fractionals.
     *
     * @param  bigDecimal  object to test
     * @param  validationRules  keeps the validation rules
     *
     * @return  true if is is out of range, else otherwise
     */
    private boolean isFractionalOutOfRange(BigDecimal bigDecimal, BigDecimalValidationRules validationRules,
        BigDecimalValidationResult result) {

        int actualFractionalPlaces = bigDecimal.scale();

        if (actualFractionalPlaces > validationRules.getMaxFractionalPlaces()) {
            result.setFailMessage(
                "The count of the digits after the point is too high. It should be less than or equal to "
                + validationRules.getMaxFractionalPlaces() + " but is " + actualFractionalPlaces + ".");

            return true;
        }

        return false;
    }


    /**
     * Checks if the value of the BigDecimal is greater than the given maximum.
     *
     * @param  bigDecimal  object to test
     * @param  validationRules  keeps the validation rules
     *
     * @return  true if is is too big, else otherwise
     */
    private boolean isTooBig(BigDecimal bigDecimal, BigDecimalValidationRules validationRules,
        BigDecimalValidationResult result) {

        if (bigDecimal.compareTo(validationRules.getMaxValue()) > 0) {
            result.setFailMessage("The value " + bigDecimal.doubleValue()
                + " is too high. It should be less than or equal to " + validationRules.getMaxValue().doubleValue()
                + ".");

            return true;
        }

        return false;
    }


    /**
     * Checks if the value of the BigDecimal is less than the given minimum.
     *
     * @param  bigDecimal  object to test
     * @param  validationRules  keeps the validation rules
     *
     * @return  true if is is too small, else otherwise
     */
    private boolean isTooSmall(BigDecimal bigDecimal, BigDecimalValidationRules validationRules,
        BigDecimalValidationResult result) {

        if (bigDecimal.compareTo(validationRules.getMinValue()) < 0) {
            result.setFailMessage("The value " + bigDecimal.doubleValue()
                + " is too small. It should be greater than or equal to " + validationRules.getMinValue().doubleValue()
                + ".");

            return true;
        }

        return false;
    }
}

package de.synyx.validator.bigdecimal;

import java.math.BigDecimal;
import java.math.BigInteger;

import static java.lang.Math.abs;


/**
 * <p>Validates a BigDecimal by minValue, maxValue value and the count of the fraction and decimal places.</p>
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class BigDecimalValidator {

    private BigDecimalValidationResult result;

    private boolean strict = false;
    private boolean allowFractions = true;

    /**
     * Returns a strict singleton instance.
     */
    public BigDecimalValidator() {

        this(true);
    }


    /**
     * Returns a strict or non strict singleton instance.
     *
     * @param  strict  precicion of BigDecimal
     */
    public BigDecimalValidator(boolean strict) {

        this(strict, true);
    }


    /**
     * Returns a singleton instance.
     *
     * @param  strict  <code>true</code> if strict parsing should be used.
     * @param  allowFractions  <code>true</code> if fractions should be checked at validation time.
     */
    public BigDecimalValidator(boolean strict, boolean allowFractions) {

        this.strict = strict;
        this.allowFractions = allowFractions;
    }

    /**
     * Validates a BigDecimal by the given validation rules..
     *
     * @param  bigDecimal  object to test
     *
     * @return  Resultobject with failure message
     */
    public BigDecimalValidationResult validate(BigDecimal bigDecimal,
        BigDecimalValidationRules bigDecimalValidationRules) {

        result = new BigDecimalValidationResult();

        BigDecimal thisBigDecimal = parse(bigDecimal);

        // return result after parsing errors
        if (!result.isValid()) {
            return result;
        }

        // calculate number if BigDecimal is set by power of x (e.g.: 1E88)
        if (thisBigDecimal.scale() < 0) {
            thisBigDecimal = new BigDecimal(thisBigDecimal.unscaledValue().multiply(
                        BigInteger.TEN.pow(abs(thisBigDecimal.scale()))));
        }

        if (isDecimalInRange(thisBigDecimal, bigDecimalValidationRules)) {
            return result;
        }

        if (allowFractions && isFractionalInRange(thisBigDecimal, bigDecimalValidationRules)) {
            return result;
        }

        if (isTooBig(thisBigDecimal, bigDecimalValidationRules)) {
            return result;
        }

        if (isTooSmall(thisBigDecimal, bigDecimalValidationRules)) {
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
    private BigDecimal parse(BigDecimal bigDecimal) {

        BigDecimal thisBigDecimal = bigDecimal;

        if (thisBigDecimal == null) {
            result.setFailMessage("Cannot parse null value.");
        } else {
            if (!allowFractions) {
                thisBigDecimal = new BigDecimal(bigDecimal.intValue());
            }

            if (strict) {
                thisBigDecimal = new BigDecimal(bigDecimal.toString());
            }
        }

        return thisBigDecimal;
    }


    /**
     * Checks for range of decimal.
     *
     * @param  bigDecimal  object to test
     * @param  bigDecimalValidationRules
     *
     * @return  true if is is out of range, else otherwise
     */
    private boolean isDecimalInRange(BigDecimal bigDecimal, BigDecimalValidationRules bigDecimalValidationRules) {

        int actualScale = bigDecimal.scale();
        int actualPrecision = bigDecimal.precision();

        if (actualPrecision <= actualScale) {
            actualPrecision = actualScale + 1;
        }

        int actualDecimalPlaces = actualPrecision - actualScale;

        if (actualDecimalPlaces < bigDecimalValidationRules.getMinDecimalPlaces()
                || actualDecimalPlaces > bigDecimalValidationRules.getMaxDecimalPlaces()) {
            result.setFailMessage("The count of the digits before the point is out of range. It should be in the range "
                + bigDecimalValidationRules.getMinDecimalPlaces() + " - "
                + bigDecimalValidationRules.getMaxDecimalPlaces()
                + " but is " + actualDecimalPlaces + ".");

            return true;
        }

        return false;
    }


    /**
     * Checks for range of fractionals.
     *
     * @param  bigDecimal  object to test
     * @param  bigDecimalValidationRules
     *
     * @return  true if is is out of range, else otherwise
     */
    private boolean isFractionalInRange(BigDecimal bigDecimal, BigDecimalValidationRules bigDecimalValidationRules) {

        int actualFractionalPlaces = bigDecimal.scale();

        if (actualFractionalPlaces > bigDecimalValidationRules.getMaxFractionalPlaces()) {
            result.setFailMessage(
                "The count of the digits after the point is too high. It should be less than or equal to "
                + bigDecimalValidationRules.getMaxFractionalPlaces() + " but is " + actualFractionalPlaces + ".");

            return true;
        }

        return false;
    }


    /**
     * Checks if the value of the BigDecimal is greater than the given maximum.
     *
     * @param  bigDecimal  object to test
     * @param  bigDecimalValidationRules
     *
     * @return  true if is is too big, else otherwise
     */
    private boolean isTooBig(BigDecimal bigDecimal, BigDecimalValidationRules bigDecimalValidationRules) {

        if (bigDecimal.compareTo(bigDecimalValidationRules.getMaxValue()) > 0) {
            result.setFailMessage("The value " + bigDecimal.doubleValue()
                + " is too high. It should be less than or equal to "
                + bigDecimalValidationRules.getMaxValue().doubleValue() + ".");

            return true;
        }

        return false;
    }


    /**
     * Checks if the value of the BigDecimal is less than the given minimum.
     *
     * @param  bigDecimal  object to test
     * @param  bigDecimalValidationRules
     *
     * @return  true if is is too small, else otherwise
     */
    private boolean isTooSmall(BigDecimal bigDecimal, BigDecimalValidationRules bigDecimalValidationRules) {

        if (bigDecimal.compareTo(bigDecimalValidationRules.getMinValue()) < 0) {
            result.setFailMessage("The value " + bigDecimal.doubleValue()
                + " is too small. It should be greater than or equal to "
                + bigDecimalValidationRules.getMinValue().doubleValue() + ".");

            return true;
        }

        return false;
    }
}

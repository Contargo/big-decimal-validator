package org.synyx.validation.bigdecimal;

import java.math.BigDecimal;


/**
 * <p>The <code>validation rules</code> specified what boundries will be validated through the validator - types
 * are:</p>
 *
 * <ul>
 * <li><code>minDecimalPlaces</code> min count of decimal places
 *
 * <ul>
 * <li>Default: 1</li>
 * </ul>
 * </li>
 * <li><code>maxDecimalPlaces</code> max count of decimal places
 *
 * <ul>
 * <li>Default: 10</li>
 * </ul>
 * </li>
 * <li><code>maxFractionalPlaces</code> max count of fractional places
 *
 * <ul>
 * <li>Default: 5</li>
 * </ul>
 * </li>
 * <li><code>minValue</code> max count of fractional places*
 *
 * <ul>
 * <li>Default: -Double.MAX_VALUE</li>
 * </ul>
 * </li>
 * <li><code>maxValue</code> max count of fractional places*
 *
 * <ul>
 * <li>Default: Double.MAX_VALUE</li>
 * </ul>
 * </li>
 * </ul>
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
public final class BigDecimalValidationRules {

    private final long minDecimalPlaces;
    private final long maxDecimalPlaces;
    private final long maxFractionalPlaces;
    private final BigDecimal minValue;
    private final BigDecimal maxValue;

    public BigDecimalValidationRules(Builder builder) {

        minDecimalPlaces = builder.minDecimalPlaces;
        maxDecimalPlaces = builder.maxDecimalPlaces;
        maxFractionalPlaces = builder.maxFractionalPlaces;
        minValue = builder.minValue;
        maxValue = builder.maxValue;
    }

    public long getMinDecimalPlaces() {

        return minDecimalPlaces;
    }


    public long getMaxDecimalPlaces() {

        return maxDecimalPlaces;
    }


    public long getMaxFractionalPlaces() {

        return maxFractionalPlaces;
    }


    public BigDecimal getMinValue() {

        return minValue;
    }


    public BigDecimal getMaxValue() {

        return maxValue;
    }

    /**
     * Builder class to build a {@link BigDecimalValidationRules} object with predefined attributes.
     */
    public static class Builder {

        private static final int MAX_DECIMAL_PLACES = 10;
        private static final int MIN_DECIMAL_PLACES = 1;
        private static final int MAX_FRACTIONAL_PLACES = 2;

        // optional
        private long minDecimalPlaces = MIN_DECIMAL_PLACES;
        private long maxDecimalPlaces = MAX_DECIMAL_PLACES;
        private long maxFractionalPlaces = MAX_FRACTIONAL_PLACES;
        private BigDecimal minValue = new BigDecimal(-Double.MAX_VALUE);
        private BigDecimal maxValue = new BigDecimal(Double.MAX_VALUE);

        public Builder minDecimalPlaces(long minDecimalPlaces) {

            this.minDecimalPlaces = minDecimalPlaces;

            return this;
        }


        public Builder maxDecimalPlaces(long maxDecimalPlaces) {

            this.maxDecimalPlaces = maxDecimalPlaces;

            return this;
        }


        public Builder maxFractionalPlaces(long maxFractionalPlaces) {

            this.maxFractionalPlaces = maxFractionalPlaces;

            return this;
        }


        public Builder minValue(double minValue) {

            this.minValue = new BigDecimal(String.valueOf(minValue));

            return this;
        }


        public Builder maxValue(double maxValue) {

            this.maxValue = new BigDecimal(String.valueOf(maxValue));

            return this;
        }


        public BigDecimalValidationRules build() {

            return new BigDecimalValidationRules(this);
        }
    }
}

package de.synyx.validator.bigdecimal;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import javax.validation.ConstraintValidatorContext;

import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Unittest of {@link BigDecimalConstraintValidator}.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class BigDecimalValidatorUnitTest {

    private BigDecimalValidator sut;

    private BigDecimal bigDecimal;
    private BigDecimalValidationRules bigDecimalValidationRules;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilderMock;

    @Before
    public void setUp() {

        sut = new BigDecimalValidator();
    }


    @Test
    public void validateMinDecimalPlaces() {

        bigDecimal = new BigDecimal("0.0");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().minValue(0).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        assertThat(result.isValid(), is(true));
    }


    @Test
    public void validateZeroZero() {

        bigDecimal = new BigDecimal("0.00");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().minValue(0).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);

        assertThat(result.isValid(), is(true));
    }


    @Test
    public void validateZero() {

        bigDecimal = new BigDecimal("0.0");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().minValue(0).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        assertThat(result.isValid(), is(true));
    }


    @Test
    public void validateTwoFractionals() {

        bigDecimal = new BigDecimal("0.01");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().minValue(0).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        assertThat(result.isValid(), is(true));
    }


    @Test
    public void validatePowerOf() {

        bigDecimal = new BigDecimal("1E10");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().minValue(0).maxDecimalPlaces(12).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        assertThat(result.isValid(), is(true));
    }


    @Test
    public void validateOneFractionalBigEnough() {

        bigDecimal = new BigDecimal("0.10");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().minValue(0).maxDecimalPlaces(3).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        assertThat(result.isValid(), is(true));
    }


    @Test
    public void validateOneFractionalAtMax() {

        bigDecimal = new BigDecimal("0.1");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().minValue(0).maxDecimalPlaces(2).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        assertThat(result.isValid(), is(true));
    }


    @Test
    public void validateMaximum() {

        bigDecimal = new BigDecimal("9999999999.99");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().minValue(0).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        assertThat(result.isValid(), is(true));
    }


    @Test
    public void validateMinimum() {

        bigDecimal = new BigDecimal("9.99");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().minValue(0).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        assertThat(result.isValid(), is(true));
    }


    @Test
    public void validateMax() {

        bigDecimal = new BigDecimal("100.05");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().maxDecimalPlaces(3).minValue(0).maxValue(
                100.05).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        assertThat(result.isValid(), is(true));
    }


    @Test
    public void validateMin() {

        bigDecimal = new BigDecimal("4.32");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().maxDecimalPlaces(1).minValue(4.32).maxValue(
                100.05).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        assertThat(result.isValid(), is(true));
    }


    @Test
    public void isNotValidFractional() {

        bigDecimal = new BigDecimal("0.01");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().maxFractionalPlaces(1).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        assertThat(result.isValid(), is(false));
    }


    @Test
    public void isNotValidWithoutFractional() {

        bigDecimal = new BigDecimal("0.01");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().maxFractionalPlaces(0).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        assertThat(result.isValid(), is(false));
    }


    @Test
    public void isNotValidWithDecimalAndFractional() {

        bigDecimal = new BigDecimal("100.01");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().maxDecimalPlaces(2).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        assertThat(result.isValid(), is(false));
    }


    @Test
    public void isNotValidMax() {

        bigDecimal = new BigDecimal("100.06");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().maxDecimalPlaces(3).minValue(0).maxValue(
                100.05).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        assertThat(result.isValid(), is(false));
    }


    @Test
    public void isNotValidMin() {

        bigDecimal = new BigDecimal("0.01");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().maxDecimalPlaces(3).minValue(0.02).maxValue(
                10).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        assertThat(result.isValid(), is(false));
    }


    @Test
    public void isNotValidPowerPositive() {

        bigDecimal = new BigDecimal("1E88");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        assertThat(result.isValid(), is(false));
    }


    @Test
    public void isNotValidPowerNegative() {

        bigDecimal = new BigDecimal("1E-88");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        assertThat(result.isValid(), is(false));
    }


    @Test
    public void isNullValue() {

        bigDecimal = null;
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        assertThat(result.isValid(), is(false));
        assertThat(result.getFailMessage(), is("Cannot parse null value."));
    }


    @Test
    public void isFractionalDisabled() {

        sut = new BigDecimalValidator(false);

        bigDecimal = new BigDecimal("100.03");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().maxFractionalPlaces(0).maxValue(100)
            .build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        assertThat(result.isValid(), is(true));
    }
}

package net.contargo.validation.bigdecimal;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import javax.validation.ConstraintValidatorContext;

import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.text.IsEmptyString.isEmptyOrNullString;


/**
 * Unittest of {@link net.contargo.validation.bigdecimal.BigDecimalConstraintValidator BigDecimalConstraintValidator}.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class BigDecimalValidatorUnitTest {

    private BigDecimalValidator sut;

    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilderMock;

    private BigDecimal bigDecimal;
    private BigDecimalValidationRules bigDecimalValidationRules;

    @Before
    public void setUp() {

        sut = new BigDecimalValidator();
    }


    @Test
    public void validateMaxDecimalPlacesBorder() {

        bigDecimal = new BigDecimal("0");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().maxDecimalPlaces(1).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        isValid(result);
    }


    @Test
    public void validateMaxDecimalPlacesOver() {

        bigDecimal = new BigDecimal("0");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().maxDecimalPlaces(2).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        isValid(result);
    }


    @Test
    public void validateMaxDecimalPlacesUnder() {

        bigDecimal = new BigDecimal("10");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().maxDecimalPlaces(1).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        isNotValid(result, "{net.contargo.validation.bigdecimal.decimaloutofrange}");
    }


    @Test
    public void validateMinDecimalPlacesBorder() {

        bigDecimal = new BigDecimal("0");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().minDecimalPlaces(1).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        isValid(result);
    }


    @Test
    public void validateMinDecimalPlacesOver() {

        bigDecimal = new BigDecimal("100");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().minDecimalPlaces(4).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        isNotValid(result, "{net.contargo.validation.bigdecimal.decimaloutofrange}");
    }


    @Test
    public void validateMinDecimalPlacesUnder() {

        bigDecimal = new BigDecimal("100");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().minDecimalPlaces(2).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        isValid(result);
    }


    @Test
    public void validateMaxFractionsBorder() {

        bigDecimal = new BigDecimal("0.00");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().maxFractionalPlaces(2).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        isValid(result);
    }


    @Test
    public void validateMaxFractionsOver() {

        bigDecimal = new BigDecimal("0.00");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().maxFractionalPlaces(3).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        isValid(result);
    }


    @Test
    public void validateMaxFractionsUnder() {

        bigDecimal = new BigDecimal("0.00");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().maxFractionalPlaces(1).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        isNotValid(result, "{net.contargo.validation.bigdecimal.fractionaloutofrange}");
    }


    @Test
    public void validateMinValueZeroBorder() {

        bigDecimal = new BigDecimal("0.0");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().minValue(0).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        isValid(result);
    }


    @Test
    public void validateMinValueZeroBorder2() {

        bigDecimal = new BigDecimal("0.00000000000000");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().minValue(0).maxFractionalPlaces(100)
            .build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        isValid(result);
    }


    @Test
    public void validateMinValueBorder() {

        bigDecimal = new BigDecimal("0.01");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().minValue(0.01).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        isValid(result);
    }


    @Test
    public void validateMinValueOver() {

        bigDecimal = new BigDecimal("0.01");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().minValue(1).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        isNotValid(result, "{net.contargo.validation.bigdecimal.toosmall}");
    }


    @Test
    public void validateMinValueUnder() {

        bigDecimal = new BigDecimal("0.01");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().minValue(0.00).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        isValid(result);
    }


    @Test
    public void validateMaxValueBorder() {

        bigDecimal = new BigDecimal("123.02");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().maxValue(123.02).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        isValid(result);
    }


    @Test
    public void validateMaxValueUnder() {

        bigDecimal = new BigDecimal("1.00");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().maxValue(0.00).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        isNotValid(result, "{net.contargo.validation.bigdecimal.toobig}");
    }


    @Test
    public void validateMaxValueOver() {

        bigDecimal = new BigDecimal("100.00");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().maxValue(124.12).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        isValid(result);
    }


    @Test
    public void validatePowerOfMaxDecimalAndValueBorder() {

        bigDecimal = new BigDecimal("1E8");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().maxValue(100000000).maxDecimalPlaces(9)
            .build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        isValid(result);
    }


    @Test
    public void validatePowerOfMaxDecimalUnder() {

        bigDecimal = new BigDecimal("1E8");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().maxDecimalPlaces(8).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        isNotValid(result, "{net.contargo.validation.bigdecimal.decimaloutofrange}");
    }


    @Test
    public void validatePowerOfMaxDecimalOver() {

        bigDecimal = new BigDecimal("1E8");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().maxDecimalPlaces(10).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        isValid(result);
    }


    @Test
    public void validatePowerOfMinDecimalAndValueBorder() {

        bigDecimal = new BigDecimal("1E2");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().minValue(100).maxDecimalPlaces(3).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        isValid(result);
    }


    @Test
    public void validatePowerOfMinDecimalUnder() {

        bigDecimal = new BigDecimal("1E3");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().minDecimalPlaces(2).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        isValid(result);
    }


    @Test
    public void validatePowerOfMinDecimalOver() {

        bigDecimal = new BigDecimal("1E2");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().minDecimalPlaces(4).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        isNotValid(result, "{net.contargo.validation.bigdecimal.decimaloutofrange}");
    }


    @Test
    public void validatePowerNegativeMaxFractionsBorder() {

        bigDecimal = new BigDecimal("1E-88");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().maxFractionalPlaces(88).build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        isValid(result);
    }


    @Test
    public void validatePowerNegativeMaxFractionsOver() {

        bigDecimal = new BigDecimal("1E-88");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        isNotValid(result, "{net.contargo.validation.bigdecimal.fractionaloutofrange}");
    }


    @Test
    public void validatePowerNegativeMaxFractionsUnder() {

        bigDecimal = new BigDecimal("1E-1");
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        isValid(result);
    }


    @Test
    public void isNullValue() {

        bigDecimal = null;
        bigDecimalValidationRules = new BigDecimalValidationRules.Builder().build();

        BigDecimalValidationResult result = sut.validate(bigDecimal, bigDecimalValidationRules);
        assertThat(result.isValid(), is(false));
        assertThat(result.getFailMessage(), is("{net.contargo.validation.bigdecimal.null}"));
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


    private void isValid(BigDecimalValidationResult result) {

        assertThat(result.isValid(), is(true));
        assertThat(result.getFailMessage(), isEmptyOrNullString());
    }


    private void isNotValid(BigDecimalValidationResult result, String expectedFailureMessage) {

        assertThat(result.isValid(), is(false));
        assertThat(result.getFailMessage(), is(expectedFailureMessage));
    }
}

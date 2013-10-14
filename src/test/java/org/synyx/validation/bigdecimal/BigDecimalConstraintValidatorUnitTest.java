package org.synyx.validation.bigdecimal;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;

import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;

import javax.validation.ConstraintValidatorContext;

import static org.hamcrest.CoreMatchers.is;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.mockito.Matchers.any;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * UnitTest of {@link BigDecimalConstraintValidator}.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
@RunWith(MockitoJUnitRunner.class)
public class BigDecimalConstraintValidatorUnitTest {

    private BigDecimalConstraintValidator sut;

    private BigDecimal bigDecimal;
    private BigDecimalValidationResult result;

    @Mock
    private BigDecimalValidator bigDecimalValidatorMock;
    @Mock
    private BigDecimalValidate bigDecimalValidateMock;
    @Mock
    private ConstraintValidatorContext constraintValidatorContextMock;
    @Mock
    private ConstraintValidatorContext.ConstraintViolationBuilder constraintViolationBuilderMock;

    @Before
    public void setUp() {

        result = new BigDecimalValidationResult();

        sut = new BigDecimalConstraintValidator();

        when(constraintValidatorContextMock.buildConstraintViolationWithTemplate(any(String.class))).thenReturn(
            constraintViolationBuilderMock);
    }


    @Test
    public void isValid() {

        bigDecimal = new BigDecimal("0.00");

        when(bigDecimalValidatorMock.validate(any(BigDecimal.class), any(BigDecimalValidationRules.class))).thenReturn(
            result);

        initializeValidationRules(1L, 10L, 2L, 0.00, 10.0);

        sut.setBigDecimalValidator(bigDecimalValidatorMock);

        assertThat(sut.isValid(bigDecimal, constraintValidatorContextMock), is(true));

        verify(constraintValidatorContextMock, times(0)).disableDefaultConstraintViolation();
        verify(constraintValidatorContextMock, times(0)).buildConstraintViolationWithTemplate(any(String.class));
        verify(constraintViolationBuilderMock, times(0)).addConstraintViolation();
    }


    @Test
    public void isNotValid() {

        result.setFailMessage("FAIL!");

        bigDecimal = new BigDecimal("0.00");

        when(bigDecimalValidatorMock.validate(any(BigDecimal.class), any(BigDecimalValidationRules.class))).thenReturn(
            result);

        initializeValidationRules(1L, 10L, 2L, 0.00, 10.0);

        sut.setBigDecimalValidator(bigDecimalValidatorMock);

        assertThat(sut.isValid(bigDecimal, constraintValidatorContextMock), is(false));

        verify(constraintValidatorContextMock).disableDefaultConstraintViolation();
        verify(constraintValidatorContextMock).buildConstraintViolationWithTemplate(any(String.class));
        verify(constraintViolationBuilderMock).addConstraintViolation();
    }


    @Test
    public void isNotValidWrongObject() {

        assertThat(sut.isValid("test", constraintValidatorContextMock), is(false));
    }


    @Test
    public void isValidNullObject() {

        assertThat(sut.isValid(null, constraintValidatorContextMock), is(true));
    }


    private void initializeValidationRules(Long minDecimalPlaces, Long maxDecimalPlaces, Long maxFractionalPlaces,
        Double min, Double max) {

        when(bigDecimalValidateMock.minDecimalPlaces()).thenReturn(minDecimalPlaces);
        when(bigDecimalValidateMock.maxDecimalPlaces()).thenReturn(maxDecimalPlaces);
        when(bigDecimalValidateMock.maxFractionalPlaces()).thenReturn(maxFractionalPlaces);
        when(bigDecimalValidateMock.minValue()).thenReturn(min);
        when(bigDecimalValidateMock.maxValue()).thenReturn(max);

        sut.initialize(bigDecimalValidateMock);
    }
}

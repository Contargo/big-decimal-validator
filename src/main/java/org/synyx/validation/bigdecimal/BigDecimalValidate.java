package org.synyx.validation.bigdecimal;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * Annotation to validate a BigDecimal by his count of pre-decimal, decimal places and min/max value.
 *
 * <pre>
   Example:

     public class Employee {

         &#064;BigDecimalValidate(minDecimalPlaces = 1, maxDecimalPlaces = 10, maxFractionalPlaces = 2,
            minValue = 0.00, maxValue = 5684.23)

         public BigDecimal salery;

          ...
      }
 * </pre>
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
@Documented
@Retention(RUNTIME)
@Target({ METHOD, FIELD, ANNOTATION_TYPE })
@Constraint(validatedBy = BigDecimalConstraintValidator.class)
public @interface BigDecimalValidate {

    String message() default "{java.math.BigDecimal.range.error}";


    Class<?>[] groups() default {};


    Class<? extends Payload>[] payload() default {};


    long maxDecimalPlaces() default Long.MAX_VALUE;


    long minDecimalPlaces() default Long.MIN_VALUE;


    long maxFractionalPlaces() default 0;


    double maxValue() default Double.MAX_VALUE;


    double minValue() default -Double.MAX_VALUE;
}

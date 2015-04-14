BigDecimal Validator
=========

The BigDecimal Validator is a small library to validate java.math.BigDecimal

# Features
* Validation of BigDecimals via Service or Annotation
  * Maximal/Minimal decimal places
  * Maximal fractional places
  * Maximal/Minimal value

#Prerequisites
 - Maven 3 or higher
 - JDK 7 or higher

## Examples

### Annotation driven

Test if the value of an attribute of an entity and the number of the decimal and fractional places are in the correct range.
```java
public class Price {

    @BigDecimalValidate(
     minDecimalPlaces = 1,
     maxDecimalPlaces = 3,
     maxFractionalPlaces = 2,
     minValue = 0.00,
     maxValue = 500.00
    )
    private BigDecimal value;
    
    // Getters and Setters
}
```

You can also specify the message that would be shown if the validation would throw an error by adding the ```message``` property.


### Service driven

```java
public void function() {

 BigDecimalValidator validator = new BigDecimalValidator();
 
 BigDecimalValidationRules rules = new BigDecimalValidationRules.Builder()
  .minDecimalPlaces(1)
  .maxDecimalPlaces(3)
  .maxFractionalPlaces(2)
  .minValue(0.00)
  .maxValue(500.00)
  .build();
 
 BigDecimalValidationResult result = validator.validate(new BigDecimal("250.00"), rules);
 
 if(!result.isValid()) {
  System.out.println(result.getFailMessage());
 }
}
```


#Getting started

Simply clone this repository
```sh
$ git clone https://github.com/Contargo/big-decimal-validator.git
```

## Build 

You can run a full build including all tests with
```sh
$ mvn clean install
```

# License

BigDecimal Validator is licensed under the Apache 2.0-license

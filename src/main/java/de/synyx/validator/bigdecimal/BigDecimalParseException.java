package de.synyx.validator.bigdecimal;

/**
 * RuntimeException for parsing errors for BigDecimal validation.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class BigDecimalParseException extends RuntimeException {

    public BigDecimalParseException() {

        super();
    }


    public BigDecimalParseException(String s) {

        super(s);
    }


    public BigDecimalParseException(String s, Throwable throwable) {

        super(s, throwable);
    }


    public BigDecimalParseException(Throwable throwable) {

        super(throwable);
    }
}

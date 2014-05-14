package net.contargo.validation.bigdecimal;

/**
 * Resultobject of the {@link BigDecimalValidator}.
 *
 * <p>If failMessage is set a error has occured.</p>
 *
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class BigDecimalValidationResult {

    private String failMessage;

    public boolean isValid() {

        return failMessage == null;
    }


    public String getFailMessage() {

        return failMessage;
    }


    public void setFailMessage(String message) {

        this.failMessage = message;
    }
}

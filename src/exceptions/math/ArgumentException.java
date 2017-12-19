package exceptions.math;

import exceptions.ParserException;

public class ArgumentException extends ParserException
{
    private final String message;
    
    public ArgumentException() {
        this.message = "Invalid arguments";
    }
    
    public ArgumentException(String message) {
        this.message = message;
    }
    
    public ArgumentException(String target, int expected, int found) {
        if(expected > found)
            this.message = String.format("Insufficient arguments for %s. Expected: %d, Found: %d", target, expected, found);
        else
            this.message = String.format("Too many arguments for %s. Expected: %d, Found: %d", target, expected, found);
    }
    
    @Override
    public String getMessage() {
        return message;
    }
}

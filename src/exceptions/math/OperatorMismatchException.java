package exceptions.math;

import exceptions.ParserException;

public class OperatorMismatchException extends ParserException
{
    public OperatorMismatchException(String message)
    {
        super(message);
    }
    
    public OperatorMismatchException()
    {
        super("Operator mismatch");
    }
}

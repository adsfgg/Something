package exceptions.math;

import exceptions.ParserException;

public class MismatchedParenthesisException extends ParserException
{
    public MismatchedParenthesisException(String message)
    {
        super(message);
    }
    
    public MismatchedParenthesisException()
    {
        super("Mismatched parenthesis");
    }
}

package exceptions.math;

import exceptions.ParserException;

public class FunctionNotFoundException extends ParserException
{
    public FunctionNotFoundException(String c)
    {
        super(String.format("Function \"%s\" doesn't exist!", c));
    }
    
    public FunctionNotFoundException()
    {
        super("Function not found!");
    }
}

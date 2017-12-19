package exceptions.math;

import exceptions.ParserException;

public class ConstantNotFoundException extends ParserException
{
    public ConstantNotFoundException(String c)
    {
        super(String.format("Constant \"%s\" doesn't exist!", c));
    }
    
    public ConstantNotFoundException()
    {
        super("Constant not found!");
    }
}

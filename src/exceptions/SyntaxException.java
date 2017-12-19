package exceptions;

public class SyntaxException extends ParserException
{
    public SyntaxException() {
        super("Invalid syntax");
    }
    
    public SyntaxException(String message) {
        super(message);
    }
    
    public SyntaxException(String expected, String found) {
        super(String.format("Syntax error. Expected: %s, Found: %s", expected, found));
    }
    
    public SyntaxException(int line, String expected, String found) {
        super(String.format("Syntax error at line %d. Expected: %s, Found: %s", line, expected, found));
    }
    
    public SyntaxException(int line) {
        super(String.format("Syntax error at line %d", line));
    }
}

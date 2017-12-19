package exceptions.language;

import exceptions.ParserException;

public class LanguageParserException extends ParserException
{
    public LanguageParserException(String message) {
        super(message);
    }
    
    public LanguageParserException() {
        super("Language parse error");
    }
}

package ui;

import exceptions.ParserException;
import helpers.InputHelper;
import helpers.NicePrint;
import interpreters.PostfixInterpreter;

import java.io.IOException;

public class PostfixUI {
    
    public static void main(String[] args) {
        
        PostfixInterpreter parser = new PostfixInterpreter();
        String infix;
        
        while(true) {
            try {
                infix = InputHelper.readConsoleLine();
                
                if(infix.equals("exit")) break;
                
                double result = parser.parse(infix);
                
                infix = NicePrint.cleanUp(infix);
                
                System.out.printf("\n%s -> %s\n\n", infix, result);
            }
            catch(ParserException e) {
                System.err.printf("\n%s\n\n", e.getMessage());
            }
        }
    }
}

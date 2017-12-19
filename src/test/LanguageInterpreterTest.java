package test;

import exceptions.language.LanguageParserException;
import helpers.InputHelper;
import helpers.MathHelper;
import interpreters.LanguageInterpreter;

import java.io.IOException;

public class LanguageInterpreterTest
{
    public static void main(String[] args) {
        LanguageInterpreter parser = new LanguageInterpreter();
        StringBuilder output;
        String line = "";
        
        while(!line.equals("quit")) {
            try {
                output = new StringBuilder();
                
                line = InputHelper.readConsoleLine();
                
                for(String s : line.split(" ")) {
                    if(MathHelper.isNumber(s) && MathHelper.isInt(Double.parseDouble(s))) {
                        String eng = parser.toEnglish(Long.parseLong(s));
                        output.append(eng);
                    }
                    else {
                        Long l = parser.toNumber(s);
                        output.append(l);
                    }
                }
                
                System.out.println(output.toString());
            }
            catch(LanguageParserException e) {
                System.out.printf("\n%s\n\n", e.getMessage());
            }
        }
    }
}

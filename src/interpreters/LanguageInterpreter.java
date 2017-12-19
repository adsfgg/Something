package interpreters;

import exceptions.language.LanguageParserException;

public class LanguageInterpreter
{
    private static final String[] NUMS = new String[] {
            "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve",
            "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
    private static final String[] TENS = new String[] {
            "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"};
    private static final String[] LARGE_NUMS = new String[] {
            "", "thousand", "million", "billion", "trillion", "quadrillion",
            "quintillion"}; // cant go higher than 9x10^18 so stop at quintillion
    
    private String toEnglishLT100(long l) {
        if(l < 20) return NUMS[(int) l];
        
        int index = (int) ((l / 10) - 1);
        String sval = TENS[index];
        
        if(l % 10 != 0) return sval + "-" + NUMS[(int) (l % 10)];
        
        return sval;
    }
    
    private String toEnglishLT1000(long l) {
        String sval = "";
        int leading = (int) (l / 100);
        int leastSig = (int) (l % 100);
        
        if(leading > 0) {
            sval = NUMS[leading] + " hundred";
            
            if(leastSig > 0) sval += " and ";
        }
        
        if(leastSig > 0) sval += toEnglishLT100(leastSig);
        
        return sval;
    }
    
    public String toEnglish(long l) throws LanguageParserException {
        if(l < 100) return toEnglishLT100(l);
        else if(l < 1000) return toEnglishLT1000(l);
        else {
            for(int i = 1; i < LARGE_NUMS.length; i++) {
                int index = i - 1;
                long value = (long) Math.pow(1000, i);
                
                if(value > l) {
                    long mod = (long) Math.pow(1000, index);
                    long leading = l / mod;
                    long remaining = l - (leading * mod);
                    
                    StringBuilder result = new StringBuilder(toEnglishLT1000(leading) + " " + LARGE_NUMS[index]);
                    
                    if(remaining > 0) {
                        if(remaining >= 100) result.append(", ");
                        else result.append(" and ");
                        
                        result.append(toEnglish(remaining));
                    }
                    
                    return result.toString();
                }
            }
        }
        
        throw new LanguageParserException(String.format("Unable to convert \"%d\" to english.", l));
    }
    
    public Long toNumber(String s) {
        return 0L; //TODO Implement this pls
    }
}

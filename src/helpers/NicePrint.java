package helpers;

import postfix.EnumConstants;

public class NicePrint
{
    private static int sqrtLength = -1;
    private static final int MAXIMUM_DIVISOR = 100;
    
    public static void print(double d) {
        print(rationalise(d));
    }
    
    public static void print(String s) {
        System.out.println();
        s = s.replace(" ", "");
        int index = s.indexOf('/');
        
        s = replaceConsts(s);
        
        if(index != -1) {
            String[] args = s.split("/");
            for(int i = 0; i < args.length; i++) {
                String s1 = args[i];
                if(MathHelper.isConst(s1)) args[i] = MathHelper.greekSymbolForConst(s1);
                else {
                    if(s1.contains("sqrt")) {
                        args[i] = nicePrintSqrt(s1.split("sqrt")[1].replaceAll("[()]", ""));
                    }
                }
            }
            System.out.println(args[0]);
            String line = "";
            
            int length;
            
            if(sqrtLength == -1) {
                length = Math.max(args[0].length(), args[1].length());
            }
            else {
                length = Math.max(sqrtLength, args[1].length());
            }
            
            for(int i = 0; i < length; i++) {
                line += "-";
            }
            System.out.println(line);
            System.out.println(args[1]);
        }
        else {
            if(MathHelper.isConst(s)) s = MathHelper.greekSymbolForConst(s);
            else {
                if(s.contains("sqrt")) {
                    s = nicePrintSqrt(s.replace("sqrt", "").replaceAll("\\(|\\)", ""));
                }
            }
            System.out.println(s);
        }
        
        sqrtLength = -1;
    }
    
    private static String nicePrintSqrt(String arg) {
        String topLine = "  _";
        String mid = "";
        String bottom = "\\|";
        
        for(int i = 1; i < arg.length(); i++) {
            topLine += "_";
        }
        
        bottom += arg;
        
        int i1 = Math.max(topLine.length(), mid.length());
        sqrtLength = Math.max(i1, bottom.length());
        
        topLine += "\n";
        
        return topLine + mid + bottom;
    }
    
    public static String replaceConsts(String s) {
        String replaced = s;
        
        for(EnumConstants consts : EnumConstants.values()) {
            replaced = replaced.replaceAll("(?i)" + consts.getName(), consts.getGreekLetter());
        }
        
        return replaced;
    }
    
    public static String rationalise(double d) {
        String r = "";
        
        if(MathHelper.isInt(d)) return "";
        
        double mult;
        
        for(int i = 2; i < MAXIMUM_DIVISOR; i++) {
            mult = d * i;
            
            if(MathHelper.isInt(mult)) {
                r += (int) (mult);
                r += "/" + i;
                break;
            }
        }
        
        double s = d * d;
        
        if(MathHelper.isInt(s)) r = String.format("sqrt(%s)", s);
        
        return r;
    }
    
    public static String cleanUp(String infix) {
        return replaceConsts(infix).replaceAll("[ ]+", "").toLowerCase();
    }
}

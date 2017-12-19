package helpers;

import exceptions.math.ConstantNotFoundException;
import exceptions.math.FunctionNotFoundException;
import postfix.EnumConstants;
import postfix.EnumFunctions;

import java.math.BigInteger;

public class MathHelper {
    /**
     * Checks if the given value is an integer.
     *
     * @param d The value to check
     *
     * @return <code>True</code> if the given value is an integer.
     */
    public static boolean isInt(double d) {
        long i = Math.round(d);
        return Math.abs(d - i) < 1e-18;
    }
    
    private static boolean isNumber(char token) {
        String numbers = "0123456789.+-";
        return numbers.contains("" + token);
    }
    
    /**
     * Checks if the given token is an operator.
     *
     * @param token The token to check
     *
     * @return <code>True</code> if the given token is an operator.
     */
    public static boolean isOperator(String token) {
        String ops = "+-*/^";
        return ops.contains(token) && !token.isEmpty();
    }
    
    /**
     * Checks if the given value is a number/
     *
     * @param s The String to check
     *
     * @return <code>True/code> if the given String is a number.
     */
    public static boolean isNumber(String s) {
        for(char c : s.toCharArray()) {
            if(!isNumber(c)) return false;
        }
        
        return !s.isEmpty() && !s.equals(".") && !s.equals("-") && !s.equals("+");
    }
    
    /**
     * Checks if the given String is a constant
     *
     * @param s The value to check.
     *
     * @return <code>True</code> if the given value is a constant.
     */
    public static boolean isConst(String s) {
        
        for(EnumConstants c : EnumConstants.values()) {
            if(c.getName().equalsIgnoreCase(s)) return true;
        }
        return false;
    }
    
    public static EnumConstants getConst(String s) throws ConstantNotFoundException {
        
        for(EnumConstants c : EnumConstants.values()) {
            if(c.getName().equalsIgnoreCase(s)) return c;
        }
        throw new ConstantNotFoundException(s);
    }
    
    public static boolean isFunction(String token) {
        for(EnumFunctions f : EnumFunctions.values()) {
            if(f.getName().equals(token.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
    
    public static EnumFunctions getFunction(String token) throws FunctionNotFoundException {
        for(EnumFunctions f : EnumFunctions.values()) {
            if(f.getName().equals(token.toLowerCase())) {
                return f;
            }
        }
        throw new FunctionNotFoundException(token);
    }
    
    public static String greekSymbolForConst(String s) {
        for(EnumConstants c : EnumConstants.values()) {
            if(c.getName().equalsIgnoreCase(s)) return c.getGreekLetter();
        }
        
        return s;
    }
    
    //TODO Implement toBase function
    public static long toBase(long n, int base, int newBase) {
        long num = 0;
        
        if(newBase > 10) throw new IllegalArgumentException("Charset must be provided for new bases greater than 10");
        
        return num;
    }
    
    public static String toBase(long n, int base, int newBase, char[] charset) {
        String num = "";
        
        return num;
    }
    
    public static long map(long x, long in_min, long in_max, long out_min, long out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }
    
    public static long factorial(long l) {
        for(long n = l - 1; n > 0; n--) {
            l *= n;
        }
        return l;
    }
    
    //TODO: Test this pls thank
    
    public static BigInteger factorial(BigInteger i) {
        for(BigInteger n = i.subtract(BigInteger.ONE); n.compareTo(BigInteger.ZERO) > 0; n = n.subtract(BigInteger.ONE)) {
            i = i.multiply(n);
        }
        
        return i;
    }
    
    public static boolean isPrime(double arg) {
        //TODO Implement efficient prime checking algorithm.
        if(arg == 0 || !MathHelper.isInt(arg)) return false;
        for(int i = 2; i < arg; i++) {
            if(arg % i == 0) return false;
        }
        return true;
    }
    
    public static boolean isHappy(double d) {
        if(!MathHelper.isInt(d)) return false;
        int total = (int) d;
        int partialSum;
        char[] s;
        int[] numbers = new int[] {0, 1, 4, 16, 20, 37, 42, 58, 89, 145};
        
        while(true) {
            s = ("" + total).toCharArray();
            total = 0;
            for(char value : s) {
                partialSum = Integer.parseInt(value + "");
                total += partialSum * partialSum;
            }
            
            for(int number : numbers) {
                if(total == number) {
                    return total == 1;
                }
                else if(total < number) break;
            }
        }
    }
}

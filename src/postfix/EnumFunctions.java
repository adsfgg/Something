package postfix;

import helpers.MathHelper;

import java.util.Random;

public enum EnumFunctions
{
    sine("sin", 1, (double[] args) -> Math.sin(args[0])),
    cosine("cos", 1, (double[] args) -> Math.cos(args[0])),
    tangent("tan", 1, (double[] args) -> Math.tan(args[0])),
    arcsine("asin", 1, (double[] args) -> Math.asin(args[0])),
    arccosine("acos", 1, (double[] args) -> Math.acos(args[0])),
    arctangent("atan", 1, (double[] args) -> Math.atan(args[0])),
    secant("sec", 1, (double[] args) -> 1 / Math.cos(args[0])),
    cosecant("cosec", 1, (double[] args) -> 1 / Math.sin(args[0])),
    cotangent("cot", 1, (double[] args) -> 1 / Math.tan(args[0])),
    squareroot("sqrt", 1, (double[] args) -> Math.sqrt(args[0])),
    cuberoot("cbrt", 1, (double[] args) -> Math.cbrt(args[0])),
    toRadians("rad", 1, (double[] args) -> Math.toRadians(args[0])),
    toDegrees("deg", 1, (double[] args) -> Math.toDegrees(args[0])),
    factorial("fact", 1, (double[] args) -> MathHelper.factorial((long) args[0])),
    negative("neg", 1, (double[] args) -> -args[0]),
    logarithmBase10("log", 1, (double[] args) -> Math.log10(args[0])),
    logarithmBaseN("logn", 2, (double[] args) -> Math.log10(args[1]) / Math.log10(args[0])),
    naturalLogorithm("ln", 1, (double[] args) -> Math.log(args[0])),
    random("rand", 0, (double[] args) -> EnumFunctions.getRandomNum()),
    randomInt("randint", 1, (double[] args) -> EnumFunctions.getRandomInt(args[0])),
    round("rnd", 1, (double[] args) -> Math.round(args[0])),
    add("add", 2, (double[] args) -> args[0] + args[1]),
    happy("happy", 1, (double[] args) -> MathHelper.isHappy(args[0]) ? 1 : 0),
    prime("prime", 1, (double[] args) -> MathHelper.isPrime(args[0]) ? 1 : 0),
    absolute("abs", 1, (double[] args) -> Math.abs(args[0]));
    
    private final String name;
    private final RunFunction check;
    private final int argsc;
    private final static Random rand = new Random();
    
    EnumFunctions(String name, int argc, RunFunction check) {
        this.name = name;
        this.argsc = argc;
        this.check = check;
    }
    
    public double run(double[] args) {
        return this.check.run(args);
    }
    
    public int getArgc() {
        return argsc;
    }
    
    public String getName() {
        return name;
    }
    
    private static double getRandomInt(double d) {
        if(MathHelper.isInt(d)) return rand.nextInt((int) d);
        throw new IllegalArgumentException("Invalid argument for function \"randint\": argument must be an integer.");
    }
    
    private static double getRandomNum() {
        return rand.nextDouble();
    }
}

@FunctionalInterface
interface RunFunction
{
    double run(double[] args);
}

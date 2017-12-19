package postfix;

public enum EnumConstants
{
    PHI("phi", "Φ", (1 + Math.sqrt(5)) / 2),
    PI("pi", "π", Math.PI),
    E("e", "e", Math.E),
    MINUS_PI("-pi", "-π", -Math.PI),
    MINUS_E("-e", "-e", -Math.E),
    MINUS_PHI("-phi", "-Φ", -(1 + Math.sqrt(5)) / 2);
    
    private final String name;
    private final String greekLetter;
    private final double value;
    
    EnumConstants(String name, String greekLetter, double value) {
        this.name = name;
        this.greekLetter = greekLetter;
        this.value = value;
    }
    
    public String getGreekLetter() {
        return greekLetter;
    } // used for pretty print
    
    public double getValue() {
        return value;
    }
    
    public String getName() {
        return name;
    }
}

package springstudy.jtsdemo;

public enum DistanceUnit {

    METER(1, "m"),
    KILOMETER(1000, "km");

    private final double multiplier;

    private final String abbreviation;

    DistanceUnit(double multiplier, String abbreviation) {
        this.multiplier = multiplier;
        this.abbreviation = abbreviation;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    @Override
    public String toString() {
        return this.abbreviation;
    }
}

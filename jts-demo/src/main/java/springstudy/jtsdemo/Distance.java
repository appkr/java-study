package springstudy.jtsdemo;

import java.io.Serializable;
import java.util.Objects;

public class Distance implements Serializable, Comparable<Distance> {

    public static final Distance ZERO = new Distance(0, DistanceUnit.METER);

    private double value;

    private DistanceUnit unit;

    public Distance(double value, DistanceUnit unit) {
        this.value = value;
        this.unit = unit;
    }

    public static Distance from (Distance source) {
        return new Distance(source.getValue(), source.getUnit());
    }

    public static Distance of(double value, DistanceUnit unit) {
        return new Distance(value, unit);
    }

    /**
     * 제출한 DistanceUnit으로 변경한 새 인스턴스를 생성합니다
     *
     * @param old 변경할 Distance 인스턴스
     * @param unit 변경하고자 하는 측정 단위
     */
    public Distance(Distance old, DistanceUnit unit) {
        this.value = old.unit.equals(unit)
            ? old.getValue()
            : old.getValue() * (old.getUnit().getMultiplier() / unit.getMultiplier());
        this.unit = unit;
    }

    /**
     * 측정 단위를 변경합니다
     *
     * @param unit 변경하고자 하는 측정 단위
     * @return Distance
     */
    public Distance in(DistanceUnit unit) {
        return new Distance(this, unit);
    }

    /**
     * 거리를 더합니다
     *
     * @param that 추가할 거리
     * @return Distance
     */
    public Distance add(Distance that) {
        return add(that, this.unit);
    }

    /**
     * 거리를 더하고, 제출한 측정 단위로 변경합니다
     *
     * @param that 추가할 거리
     * @param unit 측정 단위
     * @return Distance
     */
    public Distance add(Distance that, DistanceUnit unit) {
        Distance a = in(unit);
        Distance b = new Distance(that, unit);

        return new Distance(a.getValue() + b.getValue(), unit);
    }

    /**
     * 거리를 뺍니다
     *
     * @param that 뺄 거리
     * @return Distance
     */
    public Distance subtract(Distance that) {
        return subtract(that, this.unit);
    }

    /**
     * 거리를 빼고, 제출한 측정 단위로 변경합니다
     *
     * @param that 뺄 거리
     * @param unit 측정 단위
     * @return Distance
     */
    public Distance subtract(Distance that, DistanceUnit unit) {
        Distance a = in(unit);
        Distance b = new Distance(that, unit);
        double newValue = ((a.getValue() - b.getValue()) > 0)
            ? a.getValue() - b.getValue() : 0;

        return new Distance(newValue, unit);
    }

    /**
     * 제출한 소소점에서 반올림 합니다
     *
     * @param precision 소수점 정확도
     * @return Distance
     */
    public Distance roundUp(int precision) {
        int n = 1;
        for (int i = 0; i < precision; i++) {
            n *= 10;
        }
        double newValue = ((double) Math.round(this.value * n)) / n;

        return new Distance(newValue, unit);
    }

    // Getters

    public double getValue() {
        return value;
    }

    public DistanceUnit getUnit() {
        return unit;
    }

    // Interface impl

    @Override
    public int compareTo(Distance that) {
        if (that == null) {
            return 1;
        }

        that = new Distance(that, this.unit);
        double difference = this.value - (that.getValue());

        return difference == 0 ? 0 : difference > 0 ? 1 : -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Distance distance = (Distance) o;
        return Double.compare(distance.value, value) == 0 &&
            unit == distance.unit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, unit);
    }

    @Override
    public String toString() {
        return "Distance(" +
            "value=" + value +
            ", unit=" + unit +
            ')';
    }
}


package springstudy.jtsdemo;

import org.junit.Test;
import org.locationtech.jts.geom.Coordinate;

import static org.junit.Assert.*;

public class HaversineDistanceCalculatorTest {

    @Test
    public void testCalculate() {
        // 서울특별시청별관 @see https://geo.appkr.dev?lat=37.564363903078544&lng=126.97565350953265
        Coordinate sutA = new Coordinate(126.97565350953265, 37.564363903078544);
        // 동화면세점 @see https://geo.appkr.dev?lat=37.5697197&lng=126.9763056
        Coordinate sutB = new Coordinate(126.9763056, 37.5697197);

        Distance distance = HaversineDistanceCalculator.calculate(sutA, sutB);

        System.out.println("---");
        System.out.println(String.format("Distance between %s and %s: %.1f%s", sutA, sutB, distance.getValue(), distance.getUnit().getAbbreviation()));
        System.out.println("---");

        assertEquals(598.0, distance.roundUp(0).getValue(), 0.0);
    }
}

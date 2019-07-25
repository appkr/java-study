package springstudy.jtsdemo;

import com.vividsolutions.jts.geom.Coordinate;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class RayCastingTest {

    @Test
    public void testRayCasting() {
        Coordinate subject = new Coordinate(4.0, 8.0);

        Coordinate[] coordinates = {
            new Coordinate(2.0, 10.0),
            new Coordinate(10.0, 11.0),
            new Coordinate(5.0, 1.0),
            new Coordinate(2.0, 10.0),
        };

        ArrayList<Double> xIntersections = new ArrayList<>();
        for (int i = 0; i < coordinates.length - 1; i++) {
            Coordinate curr = coordinates[i];
            Coordinate next = coordinates[i + 1];

            // 기울기: ⊿ y / ⊿ x
            // Y 절편: y = ax + b, b = y - ax
            double a = (next.y - curr.y) / (next.x - curr.x);
            double b = curr.y - (a * curr.x);

            // 도형과 테스트하고자하는 좌표(subject)를 지나는 "임의의" 직선과의 교점을 구한다
            // x = (y - b) / a
            double x = (subject.y - b) / a;

            // 비교하는 두점 사이에 있는 교점만 유효한 것으로 판단한다
            // 중점 m을 구한다
            // 한점과 중점의 거리 d = (|a - m| or |b - m|)을 구한다
            // 교점과 중점과의 거리 |x - m|이 d 보다 작아야 한다
            double m = (curr.x + next.x) / 2;
            double d = Math.abs(curr.x - m);
            boolean valid = Math.abs(x - m) <= d;
            if (valid) {
                xIntersections.add(x);
            }
        }

        // 교점이 몇번째인지 판단한다
        // 0번째, 마지막번째를 임의로 넣어 준다
        xIntersections.add(Double.MIN_VALUE);
        xIntersections.add(Double.MAX_VALUE);
        xIntersections.sort(Double::compare);

        boolean within = false;
        for (int i = 0; i < xIntersections.size() - 1; i++) {
            Double currX = xIntersections.get(i);
            Double nextX = xIntersections.get(i + 1);

            if (currX < subject.x && subject.x < nextX) {
                // MIN    currX        subject.x          nextX   MAX
                // |------|-----------------|-----------------|------|
                // 0      1                                   2      3
                within = i % 2 == 1;
                break;
            }
        }

        assertTrue(within);
    }
}

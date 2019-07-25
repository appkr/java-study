package springstudy.jtsdemo;

import com.vividsolutions.jts.geom.*;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JtsTest {

    private static final int WGS84 = 4326;
    private static final GeometryFactory gf = new GeometryFactory(new PrecisionModel(), WGS84);

    @Test
    public void testWithin() {
        Coordinate[] coordinates = {
            new Coordinate(126.9666744, 37.5657828), // 서대문역
            new Coordinate(126.9718135, 37.5700332), // 흥국파이낸스그룹
            new Coordinate(126.9770048, 37.5701374), // 세종대로사거리
            new Coordinate(126.9770309, 37.5639812), // 시청역
            new Coordinate(126.9696735, 37.5620195), // 경찰청
            new Coordinate(126.9666744, 37.5657828), // 서대문역
        };

        Polygon polygon = gf.createPolygon(coordinates);

        Point sut1 = gf.createPoint(new Coordinate(126.9753259, 37.5657223)); // 덕수궁
        Point sut2 = gf.createPoint(new Coordinate(126.9685533, 37.5706855)); // 경희궁

        assertTrue(sut1.within(polygon));
        assertFalse(sut2.within(polygon));
    }

    @Test
    public void testOnTheEdge() {
        Coordinate[] coordinates = {
            new Coordinate(1.0, 1.0),
            new Coordinate(1.0, 2.0),
            new Coordinate(2.0, 2.0),
            new Coordinate(2.0, 1.0),
            new Coordinate(1.0, 1.0),
        };

        Polygon polygon = gf.createPolygon(coordinates);
        Point sut = gf.createPoint(new Coordinate(1.0, 1.5));

        assertFalse(sut.within(polygon));
    }

    @Test
    public void testCoveredBy1() {
        Coordinate[] coordinates = {
            new Coordinate(1.0, 1.0),
            new Coordinate(1.0, 2.0),
            new Coordinate(2.0, 2.0),
            new Coordinate(2.0, 1.0),
            new Coordinate(1.0, 1.0),
        };

        Polygon polygon = gf.createPolygon(coordinates);
        Point sut = gf.createPoint(new Coordinate(1.0, 1.5));

        assertTrue(sut.coveredBy(polygon));
    }

    @Test
    public void testCoveredBy2() {
        Coordinate[] coordinates = {
            new Coordinate(1.0, 1.0),
            new Coordinate(1.0, 2.0),
            new Coordinate(2.0, 2.0),
            new Coordinate(2.0, 1.0),
            new Coordinate(1.0, 1.0),
        };

        Polygon polygon = gf.createPolygon(coordinates);
        Point sut = gf.createPoint(new Coordinate(0.5, 1.0));

        assertFalse(sut.coveredBy(polygon));
    }
}

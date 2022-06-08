package springstudy.jtsdemo;

import org.geotools.geometry.jts.JTS;
import org.locationtech.jts.geom.Coordinate;
import org.opengis.referencing.operation.TransformException;

import static org.geotools.referencing.crs.DefaultGeographicCRS.WGS84;

public class OrthodromicDistanceCalculator {

    /**
     * a, b 두 지점간의 거리를 구합니다.
     *
     * @param a
     * @param b
     * @return Distance in Meters
     * @see https://docs.geotools.org/stable/userguide/library/referencing/calculator.html
     *      orthodromicDistance() API가 GeodeticCalculator를 이용해서 meter 단위로 결과를 계산해 줌
     */
    public static Distance calculate(Coordinate a, Coordinate b) {
        double distance = 0.0;
        try {
            distance = JTS.orthodromicDistance(a, b, WGS84);
        } catch (TransformException e) {
            // do nothing
        }

        return new Distance(distance, DistanceUnit.METER);
    }
}

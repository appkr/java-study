package springstudy.jtsdemo;

import org.locationtech.jts.geom.Coordinate;

public class HaversineDistanceCalculator {

    /**
     * a, b 두 지점간의 거리를 구합니다.
     *
     * @param a
     * @param b
     * @return Distance in Meters
     */
    public static Distance calculate(Coordinate a, Coordinate b) {
        double distance = haversine(a.getY(), b.getY(),
                                    a.getX(), b.getX(),
                                    0.0, 0.0);

        return new Distance(distance, DistanceUnit.METER);
    }

    /**
     * https://stackoverflow.com/a/16794680
     *
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     *
     * Note. latitude means Y, longitude means X
     *
     * @return Distance in Meters
     */
    private static double haversine(double lat1, double lat2,
                                    double lon1, double lon2,
                                    double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }
}

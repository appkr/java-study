//package java.springstudy.jtsdemo;
///**
// * Referenced from:
// * https://www.programcreek.com/java-api-examples/?code=ianturton/geotools-cookbook/geotools-cookbook-master/modules/distances/src/main/java/org/ianturton/cookbook/distances/OrthodromicDistance.java
// */
//
//import org.geotools.geometry.jts.JTS;
//import org.geotools.referencing.crs.DefaultGeographicCRS;
//import org.junit.Test;
//import org.locationtech.jts.geom.Coordinate;
//import org.locationtech.jts.geom.GeometryFactory;
//import org.locationtech.jts.geom.Point;
//import org.opengis.referencing.operation.TransformException;
//
//public class OrthodromicDistanceTest {
//
//    private static final DefaultGeographicCRS defaultCrs = DefaultGeographicCRS.WGS84;
//    private static final GeometryFactory gf = new GeometryFactory();
//
//    @Test
//    public void testCanCalculateDistance() {
//        Point a = gf.createPoint(new Coordinate(126.9666744, 37.5657828));
//        Point b = gf.createPoint(new Coordinate(126.9770309, 37.5639812));
//    }
//
//    private Measure calculateDistance(Point a, Point b) throws TransformException {
//        double distance = JTS.orthodromicDistance(a.getCoordinate(), b.getCoordinate(), defaultCrs);
//        Measure<Double, Length> measure = Measure.valueOf(distance, SI.METER);
//        // measure.doubleValue(SI.KILOMETER);
//
//        return measure;
//    }
//}

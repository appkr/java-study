package springstudy.jtsdemo;

import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.geometry.jts.JTS;
import org.geotools.measure.Measure;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultGeographicCRS;
import org.junit.Test;
import org.locationtech.jts.geom.*;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.geometry.MismatchedDimensionException;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.opengis.referencing.operation.MathTransform;
import org.opengis.referencing.operation.TransformException;
import si.uom.SI;

import java.util.Arrays;

public class GeometryApiTest {

  private static final int WGS84 = 4326;
  private static final GeometryFactory gf = new GeometryFactory(new PrecisionModel(), WGS84);

  @Test
  public void getArea() {
    Coordinate[] coordinates = {
        new Coordinate(126.9666744, 37.5657828), // 서대문역
        new Coordinate(126.9718135, 37.5700332), // 흥국파이낸스그룹
        new Coordinate(126.9770048, 37.5701374), // 세종대로사거리
        new Coordinate(126.9770309, 37.5639812), // 시청역
        new Coordinate(126.9696735, 37.5620195), // 경찰청
        new Coordinate(126.9666744, 37.5657828), // 서대문역
    };

    Polygon polygon = gf.createPolygon(coordinates);

    System.out.println("---");
    System.out.println(String.format("Area of 서대문-흥국파이낸스그룹-세종대로사거리-시청역-경찰청-서대문역 is %f", polygon.getArea())); // 0.000059
    System.out.println(String.format("Length of 서대문-흥국파이낸스그룹-세종대로사거리-시청역-경찰청-서대문역 is %f", polygon.getLength())); // 0.030444
    System.out.println("---");
  }

//  SimpleFeature buildSimpleFeature(Polygon polygon) {
//    // TODO need to convert to SimpleFeatureCollection
//    // @see https://www.programcreek.com/java-api-examples/?api=org.opengis.feature.simple.SimpleFeature
//    SimpleFeatureTypeBuilder b = new SimpleFeatureTypeBuilder();
//    b.setName("typename");
//    b.setCRS(DefaultGeographicCRS.WGS84);
//    b.add("the_geom", Polygon.class);
//    b.add("AttrName", String.class);
//    SimpleFeatureType type = b.buildFeatureType();
//    SimpleFeatureBuilder builder = new SimpleFeatureBuilder(type);
//    Object[] values = polygon.getCoordinates();
//    System.out.println(Arrays.toString(values));
//    builder.addAll(values);
//    SimpleFeature feature = builder.buildFeature(type.getTypeName());
//    return feature;
//  }
//
//  Measure calcArea(SimpleFeature feature) {
//    // @see https://gis.stackexchange.com/a/250419
//    Polygon p = (Polygon) feature.getDefaultGeometry();
//    Point centroid = p.getCentroid();
//    try {
//      String code = "AUTO:42001," + centroid.getX() + "," + centroid.getY();
//      CoordinateReferenceSystem auto = CRS.decode(code);
//      MathTransform transform = CRS.findMathTransform(DefaultGeographicCRS.WGS84, auto);
//      Polygon projed = (Polygon) JTS.transform(p, transform);
//      return new Measure(projed.getArea(), SI.SQUARE_METRE);
//    } catch (MismatchedDimensionException | TransformException | FactoryException e) {
//      e.printStackTrace();
//    }
//    return new Measure(0.0, SI.SQUARE_METRE);
//  }
}

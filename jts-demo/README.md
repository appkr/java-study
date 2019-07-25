## JTS Demo
- http://woowabros.github.io/experience/2018/03/31/hello-geofence.html

#### Useful API
```java
private static final int WGS84 = 4326;
private static final GeometryFactory gf = new GeometryFactory(new PrecisionModel(), WGS84);

// ---

GeometryFactory
+ createPoint(CoordinateSequence)
+ createGeometryCollection(Geometry[])
+ createPoint(Coordinate)
+ createMultiPolygon(Polygon[])
+ createPolygon(shell:LineRing, holes:LineRing)
+ createPolygon(CoordinateSequence)
+ createPolygon(Coordinate[])
```

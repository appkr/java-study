plugins {
    java
    application
}

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation("com.google.guava:guava:27.0.1-jre")
    compile("org.locationtech.jts:jts-core:1.16.1")
//    compile("org.opengis:geoapi:3.0.1")
//    compile("org.geotools:gt-main:21.1")
//    compile("javax.measure:unit-api:2.0")
    testImplementation("junit:junit:4.12")
}

application {
    mainClassName = "springstudy.jtsdemo.App"
}

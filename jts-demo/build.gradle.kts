plugins {
    java
    application
}

repositories {
    maven {
        setUrl("https://repo.osgeo.org/repository/release/")
    }
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("com.google.guava:guava:27.0.1-jre")
    implementation("org.locationtech.jts:jts-core:1.18.2")
    implementation("org.locationtech.jts:jts-modules:1.18.2")
    implementation("org.geotools:gt-main:27.0")

    testImplementation("junit:junit:4.12")
}

application {
    mainClassName = "springstudy.jtsdemo.App"
}

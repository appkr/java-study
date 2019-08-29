plugins {
    java
    application
}

repositories {
    maven {
        setUrl("http://maven.geomajas.org")
    }
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("com.google.guava:guava:27.0.1-jre")
    compile("org.locationtech.jts:jts-core:1.16.1")
    compile("org.locationtech.jts:jts-modules:1.16.1")
    compile("org.geotools:gt-main:21.2")

    testImplementation("junit:junit:4.12")
}

application {
    mainClassName = "springstudy.jtsdemo.App"
}

plugins {
    java
    application
}

repositories {
    jcenter()
}

dependencies {
    implementation("com.google.guava:guava:27.0.1-jre")
    compile("org.locationtech.jts:jts-core:1.16.1")
    testImplementation("junit:junit:4.12")
}

application {
    mainClassName = "springstudy.jtsdemo.App"
}

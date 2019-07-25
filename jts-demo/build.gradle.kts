plugins {
    java
    application
}

repositories {
    jcenter()
}

dependencies {
    implementation("com.google.guava:guava:27.0.1-jre")
    compile("com.vividsolutions:jts:1.13")
    testImplementation("junit:junit:4.12")
}

application {
    mainClassName = "springstudy.jtsdemo.App"
}

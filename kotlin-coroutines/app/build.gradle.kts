plugins {
    kotlin("jvm") version "1.9.21"
    application
}

repositories { // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.3")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

application {
    mainClass.set("dev.appkr.coroutines.AppKt")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

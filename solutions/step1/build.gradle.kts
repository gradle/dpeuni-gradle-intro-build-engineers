plugins {
    application
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(libs.junit.jupiter)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation(libs.guava)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "org.example.App"
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.register<GenerateRandomFile>("createLargeOutputFile") {
    sizeInBytes = 1024L * 1024L * 2L
    outputFile = layout.buildDirectory.file("outputFile.pkg")
}

tasks.named("build") {
    dependsOn("createLargeOutputFile")
}

tasks.register<CheckFileSize>("validateOutputSize") {

}

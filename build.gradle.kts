plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
}

group = "zs1.timetabler"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

application {
    mainModule = "zs1.timetabler"
    mainClass = "zs1.timetabler.TimetablerApplication"
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.postgresql:postgresql:42.6.0")
    implementation("org.hibernate.orm:hibernate-core:6.6.3.Final")
}

tasks.test {
    useJUnitPlatform()
}

javafx {
    version = "23.0.1"
    modules("javafx.controls", "javafx.fxml")
}
//
//application {
//    mainClass = "TimetablerApplication"
//}
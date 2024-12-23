import com.avast.gradle.dockercompose.ComposeExtension

plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("com.avast.gradle.docker-compose") version "0.17.11"
}

group = "zs1.timetabler"
version = "1.0-SNAPSHOT"

//apply(plugin = 'docker-compose')
//apply plugin: 'docker-compose'

//dockerCompose.isRequiredBy(this.tasks.run)
//dockerCompose.isRequiredBy(this.tasks.test)

apply(plugin = "docker-compose")
configure<ComposeExtension> {
    includeDependencies.set(true)
    createNested("local").apply {
        setProjectName("timetabler")
        environment.putAll(mapOf("TAGS" to "feature-test,local"))
        startedServices.set(listOf("foo-api", "foo-integration"))
        upAdditionalArgs.set(listOf("--no-deps"))
    }
}

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
    implementation("jakarta.transaction:jakarta.transaction-api:2.0.1")
    implementation("jakarta.enterprise:jakarta.enterprise.cdi-api:4.1.0")
    implementation("org.jboss.logging:jboss-logging:3.4.1.Final")
    implementation("com.fasterxml:classmate:1.7.0")
    implementation("jakarta.xml.bind:jakarta.xml.bind-api:4.0.2")
    implementation("org.hibernate.common:hibernate-commons-annotations:7.0.3.Final")
    implementation("net.bytebuddy:byte-buddy:1.15.10");
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
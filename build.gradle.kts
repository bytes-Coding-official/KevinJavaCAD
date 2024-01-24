plugins {
    id("java")
    //javafx plugin
    id("application")
    id("com.github.johnrengelman.shadow") version "+"
    id("org.openjfx.javafxplugin") version "+"
}
group = "de.kevin"
version = "1.0.2"
description = "CAD UI Programm"
repositories {
    mavenCentral()
}
javafx {
    version = "+"
    modules("javafx.controls", "javafx.fxml")

}


dependencies {
    //dependency junit
    testImplementation("org.junit.jupiter:junit-jupiter-api:+")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:+")
    implementation(fileTree(mapOf("dir" to "jar", "include" to listOf("*.jar"))))
}
tasks {
    shadowJar {
        destinationDirectory.set(file("$projectDir"))
    }
    compileJava {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
        options.release.set(19)
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
    }
    processResources {
        filteringCharset = Charsets.UTF_8.name() // We want UTF-8 for everything
    }

    test {
        useJUnitPlatform()
    }
    register<Copy>("copyTask") {
        from("sourceDirectory")
        into("destinationDirectory")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
}
application {
    mainClass.set("application.Main")
}

plugins {
    `java-library`
}

group = "net.orekyuu"
version = "1.0-SNAPSHOT"

subprojects {
    apply(plugin = "java-library")

    repositories {
        mavenCentral()
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
        toolchain {
            languageVersion = JavaLanguageVersion.of(8)
        }
        withSourcesJar()
        withJavadocJar()
    }

    dependencies {
        testImplementation(platform(rootProject.libs.junitBom))
        testImplementation(rootProject.libs.junitJupiter)
    }

    tasks {
        withType<JavaCompile> {
            options.encoding = "UTF-8"
        }
        test {
            useJUnitPlatform()
        }
    }
}
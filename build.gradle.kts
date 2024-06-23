plugins {
    `java-library`
    alias(libs.plugins.spotless)
}

group = "net.orekyuu"
version = "1.0-SNAPSHOT"

val integrationTests: List<Project> = findProject("integration-test")!!.subprojects.toList()

subprojects {
    apply(plugin = "java-library")
    apply(plugin = rootProject.libs.plugins.spotless.get().pluginId)

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

    spotless {
        java {
            googleJavaFormat("1.17.0")
        }
    }

    dependencies {
        testImplementation(platform(rootProject.libs.junitBom))
        testImplementation(rootProject.libs.junitJupiter)
        testImplementation(rootProject.libs.assertj)
    }

    tasks {
        withType<JavaCompile> {
            options.encoding = "UTF-8"
        }
        test {
            useJUnitPlatform()
        }
        build {
            dependsOn(spotlessApply)
        }
    }
}

configure(integrationTests) {
    apply(plugin = "java-library")

    dependencies {
        implementation(project(":core"))
        // integration-test library
        testImplementation(project(":integration-test"))
        // test containers
        testImplementation(platform(rootProject.libs.testContainersBom))

        // postgresql
        testImplementation(rootProject.libs.testContainersPostgresql)
        testImplementation(rootProject.libs.postgresqlDriver)
        // mysql
        testImplementation(rootProject.libs.testContainersMysql)
        testImplementation(rootProject.libs.mysqlDriver)
    }

    tasks {
        test {
            maxHeapSize = "1g"
            useJUnitPlatform()
        }
    }
}

project(":integration-test") {
    dependencies {
        implementation(platform(rootProject.libs.junitBom))
        implementation(rootProject.libs.junitJupiter)
        implementation(rootProject.libs.assertj)
    }
}
plugins {
    `java-library`
}

group = "net.orekyuu"
version = "1.0-SNAPSHOT"

val integrationTests: List<Project> = findProject("integration-test")!!.subprojects.toList()

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
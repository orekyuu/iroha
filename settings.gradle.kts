plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version("0.10.0")
}
rootProject.name = "iroha"

include(":core")
include(":integration-test")
include(":integration-test:mysql")
include(":integration-test:postgresql")
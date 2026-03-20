pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

rootProject.name = "SongReco"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(":composeApp")
include(":features:root:api")
include(":features:root:impl")
include(":features:search:api")
include(":features:search:impl")
include(":features:trending:api")
include(":features:trending:impl")
include(":features:list:api")
include(":features:list:impl")
include(":features:details:api")
include(":features:details:impl")
include(":core:webapi")
include(":core:webapi:testdoubles")
include(":core:designsystem")
include(":core:arch")
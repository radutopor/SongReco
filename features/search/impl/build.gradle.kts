plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose.plugin)
}

kotlin {
    jvm()
    listOf(iosX64(), iosArm64(), iosSimulatorArm64())

    sourceSets {
        commonMain.dependencies {
            api(projects.features.search.api)

            implementation(projects.core.arch)
            implementation(projects.core.designsystem)

            implementation(projects.features.list.api)

            implementation(libs.koin.core)
            implementation(libs.compose.material3)
            implementation(libs.compose.resources)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}
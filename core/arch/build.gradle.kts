plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose.plugin)
}

kotlin {
    jvm()
    listOf(iosX64(), iosArm64(), iosSimulatorArm64())

    sourceSets.commonMain.dependencies {
        implementation(libs.kotlinx.coroutines.core)
        implementation(libs.compose.foundation)
        implementation(libs.compose.resources)
    }
}
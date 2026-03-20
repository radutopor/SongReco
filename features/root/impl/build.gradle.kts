plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose.plugin)
    alias(libs.plugins.android.library)
}

kotlin {
    androidTarget {}
    listOf(iosX64(), iosArm64(), iosSimulatorArm64())

    sourceSets {
        commonMain.dependencies {
            api(projects.features.root.api)

            implementation(projects.core.arch)
            implementation(projects.core.designsystem)

            implementation(projects.features.search.impl)
            implementation(projects.features.trending.impl)
            implementation(projects.features.list.impl)
            implementation(projects.features.details.impl)

            implementation(libs.koin.core)
            implementation(libs.compose.material3)
            implementation(libs.compose.resources)
            implementation(libs.compose.ui.backhandler)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}

android {
    namespace = "com.radutopor.songreco.features.root"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
}
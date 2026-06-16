plugins {
    alias(libs.plugins.movieapp.android.application)
    alias(libs.plugins.movieapp.android.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.movieapp"
    defaultConfig {
        applicationId = "com.movieapp"
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(projects.movieApp.feature.catalogue.api)
    implementation(projects.movieApp.feature.catalogue.impl)
    implementation(projects.movieApp.feature.splash.api)
    implementation(projects.movieApp.feature.splash.impl)
    implementation(projects.movieApp.feature.favorites.api)
    implementation(projects.movieApp.feature.favorites.impl)
    implementation(projects.movieApp.feature.details.api)
    implementation(projects.movieApp.feature.details.impl)
    // Core modules
    implementation(projects.core.common)
    implementation(projects.core.ui)
    implementation(projects.core.designsystem)
    implementation(projects.core.data)
    implementation(projects.core.model)

    // AndroidX + Compose
    implementation(libs.androidx.activity.compose)
    implementation(libs.material)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(projects.core.navigation)

    //Serialization
    implementation(libs.kotlinx.serialization.json)
}
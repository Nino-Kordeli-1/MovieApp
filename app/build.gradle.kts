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
    implementation(projects.feature.catalogue.api)
    implementation(projects.feature.catalogue.impl)
    implementation(projects.feature.splash.api)
    implementation(projects.feature.splash.impl)
    implementation(projects.feature.favorites.api)
    implementation(projects.feature.favorites.impl)
    implementation(projects.feature.details.api)
    implementation(projects.feature.details.impl)
    // Core modules
    implementation(projects.core.common)
    implementation(projects.core.ui)
    implementation(projects.core.designsystem)
    implementation(projects.core.data)
    implementation(projects.core.model)

    // AndroidX + Compose
    implementation(libs.androidx.activity.compose)
    implementation(libs.material)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtimeCompose)

    //Serialization
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.koin.android)
}
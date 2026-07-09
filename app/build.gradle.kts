plugins {
    alias(libs.plugins.movieapp.android.application)
    alias(libs.plugins.movieapp.android.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
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
    implementation(projects.feature.home.api)
    implementation(projects.feature.home.impl)
    implementation(projects.feature.splash.api)
    implementation(projects.feature.splash.impl)
    implementation(projects.feature.favorites.api)
    implementation(projects.feature.favorites.impl)
    implementation(projects.feature.details.api)
    implementation(projects.feature.details.impl)
    implementation(projects.core.database)
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

    //Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
}
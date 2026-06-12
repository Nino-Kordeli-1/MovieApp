plugins {
    alias(libs.plugins.movieapp.android.application)
    alias(libs.plugins.movieapp.android.application.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.movieapp"
    defaultConfig {
        applicationId = "com.example.movieapp"
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
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtimeCompose)

    //Serialization
    implementation(libs.kotlinx.serialization.json)
}
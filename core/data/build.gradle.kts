plugins {
    alias(libs.plugins.movieapp.android.library)
    alias(libs.plugins.movieapp.koin.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.movieapp.data"
    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
    api(projects.core.common)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit)
    implementation(libs.okhttp.logging.interceptor)
}
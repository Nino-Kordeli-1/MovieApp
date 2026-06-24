plugins {
    alias(libs.plugins.movieapp.android.library)
    alias(libs.plugins.movieapp.koin.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.movieapp.data"
    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
    api(projects.core.common)
    api(projects.core.domain)
    api(projects.core.network)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation(libs.retrofit)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.androidx.annotation.experimental)
}
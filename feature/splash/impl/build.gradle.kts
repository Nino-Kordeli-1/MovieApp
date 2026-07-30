plugins {
    alias(libs.plugins.movieapp.android.feature.impl)
    alias(libs.plugins.movieapp.android.compose)
}

android {
    namespace = "com.movieapp.impl.splash"
    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
    implementation(projects.core.common)
    implementation(projects.core.domain)
    implementation(projects.feature.splash.api)
    implementation(projects.feature.home.api)
    implementation(libs.androidx.activity.compose)
}
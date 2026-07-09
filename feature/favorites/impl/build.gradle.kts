plugins {
    alias(libs.plugins.movieapp.android.feature.impl)
    alias(libs.plugins.movieapp.android.compose)
}

android {
    namespace = "com.movieapp.impl.favorites"
    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.common)
    implementation(projects.feature.favorites.api)
    implementation(projects.feature.details.api)
    implementation(libs.androidx.activity.compose)
}
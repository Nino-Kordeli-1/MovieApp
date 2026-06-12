plugins {
    alias(libs.plugins.movieapp.android.feature.impl)
    alias(libs.plugins.movieapp.android.library.compose)
}

android {
    namespace = "com.example.impl.catalogue"
    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.feature.catalogue.api)
    implementation(libs.androidx.activity.compose)
}
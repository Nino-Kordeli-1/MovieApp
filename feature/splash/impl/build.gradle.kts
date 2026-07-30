plugins {
    alias(libs.plugins.movieapp.android.feature.impl)
}

android {
    namespace = "com.movieapp.impl.splash"
}

dependencies {
    implementation(projects.feature.splash.api)
    implementation(projects.feature.home.api)
}
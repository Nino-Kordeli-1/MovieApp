plugins {
    alias(libs.plugins.movieapp.android.feature.api)
}

android {
    namespace = "com.example.core.splash"
}

dependencies {
    api(projects.core.navigation)
    implementation(projects.core.domain)
}
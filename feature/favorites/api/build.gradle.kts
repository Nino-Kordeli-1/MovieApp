plugins {
    alias(libs.plugins.movieapp.android.feature.api)
}

android {
    namespace = "com.example.core.favorites"
}

dependencies {
    api(projects.core.navigation)
    implementation(projects.core.domain)
}
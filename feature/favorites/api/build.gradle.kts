plugins {
    alias(libs.plugins.movieapp.android.feature.api)
}

android {
    namespace = "com.movieapp.core.favorites"
}

dependencies {
    api(projects.core.navigation)
    implementation(projects.core.domain)
}
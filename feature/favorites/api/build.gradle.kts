plugins {
    alias(libs.plugins.movieapp.android.feature.api)
}

android {
    namespace = "com.movieapp.core.favorites"
}

dependencies {
    api(projects.core.navigation)
    api(projects.core.domain)
    implementation(projects.core.domain)
}
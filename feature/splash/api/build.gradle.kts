plugins {
    alias(libs.plugins.movieapp.android.feature.api)
}

android {
    namespace = "com.movieapp.core.splash"
}

dependencies {
    api(projects.core.navigation)
    implementation(projects.core.domain)
}
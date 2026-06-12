plugins {
    alias(libs.plugins.movieapp.android.feature.api)
}

android {
    namespace = "com.example.core.catalogue"
}

dependencies {
    api(projects.core.navigation)
    implementation(projects.core.domain)
}
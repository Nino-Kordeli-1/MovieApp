plugins {
    alias(libs.plugins.movieapp.android.feature.impl)
}

android {
    namespace = "com.movieapp.impl.details"
}

dependencies {
    implementation(projects.feature.details.api)
}
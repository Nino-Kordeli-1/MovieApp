plugins {
    alias(libs.plugins.movieapp.android.feature.impl)
}

android {
    namespace = "com.movieapp.impl.favorites"
}

dependencies {
    implementation(projects.feature.favorites.api)
    implementation(projects.feature.details.api)
}
import org.gradle.kotlin.dsl.dependencies

plugins {
    alias(libs.plugins.movieapp.android.feature.impl)
}

android {
    namespace = "com.movieapp.impl.home"
}

dependencies {
    implementation(projects.feature.home.api)
    implementation(projects.feature.details.api)
}
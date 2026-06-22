plugins {
    alias(libs.plugins.movieapp.android.library)
    alias(libs.plugins.movieapp.koin.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.movieapp.data"
    testOptions.unitTests.isIncludeAndroidResources = true

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        val properties = com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(
            rootDir,
            providers
        )
        buildConfigField(
            "String",
            "TMDB_TOKEN",
            "\"${properties.getProperty("TMDB_READ_ACCESS_TOKEN")}\""
        )
    }
}

dependencies {
    api(projects.core.common)
    api(projects.core.domain)
    implementation(libs.retrofit.kotlinx.serialization.converter)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit)
    implementation(libs.okhttp.logging.interceptor)
}
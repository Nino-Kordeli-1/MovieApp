plugins {
    alias(libs.plugins.movieapp.android.library)
    alias(libs.plugins.movieapp.koin.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.movieapp.network"

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
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)

    implementation(libs.retrofit.kotlinx.serialization)
    implementation(libs.okhttp.logging.interceptor)
}
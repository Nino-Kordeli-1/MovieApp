plugins {
    alias(libs.plugins.movieapp.jvm.library)
    alias(libs.plugins.movieapp.koin.library)
    alias(libs.plugins.kotlin.serialization)
}
dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
}

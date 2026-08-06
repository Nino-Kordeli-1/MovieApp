plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.common)
    implementation(libs.koin.core)
    implementation(libs.kotlinx.coroutines.core)
}
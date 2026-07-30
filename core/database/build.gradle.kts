plugins {
    alias(libs.plugins.movieapp.android.library)
    alias(libs.plugins.ksp)
    alias(libs.plugins.movieapp.koin.library)
}
android {
    namespace = "com.movieapp.database"
    ksp{
        arg("room.schemaLocation", "$projectDir/schemas")
    }
}

dependencies {
    api(projects.core.model)
    api(projects.core.common)
    implementation(projects.core.domain)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
}
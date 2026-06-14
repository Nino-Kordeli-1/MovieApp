import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
    alias(libs.plugins.android.lint)
}

group = "com.movieapp.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "movieapp.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidLibrary") {
            id = "movieapp.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("root") {
            id = "movieapp.root"
            implementationClass = "RootPlugin"
        }

        register("androidFeatureImpl") {
            id = "movieapp.android.feature.impl"
            implementationClass = "AndroidFeatureImplConventionPlugin"
        }

        register("androidFeatureApi") {
            id = "movieapp.android.feature.api"
            implementationClass = "AndroidFeatureApiConventionPlugin"
        }

        register("koinLibrary") {
            id = "movieapp.koin.library"
            implementationClass = "KoinConventionPlugin"
        }

        register("jvmLibrary") {
            id = "movieapp.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }

        register("testingLibrary") {
            id = "movieapp.test.library"
            implementationClass = "TestingLibraryConventionPlugin"
        }
        register("androidCompose") {
            id = "movieapp.android.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }
    }
}
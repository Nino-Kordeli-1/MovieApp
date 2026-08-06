package com.movieapp

import com.android.build.api.dsl.CommonExtension
import com.extensions.debugImplementationLibrary
import com.extensions.implementationLibrary
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension
) {
    commonExtension.buildFeatures.compose = true

    dependencies {
        val bom = libs.findLibrary("androidx-compose-bom").get()
        add("implementation", platform(bom))
        add("androidTestImplementation", platform(bom))
    }
    implementationLibrary("androidx-compose-ui")
    implementationLibrary("androidx-compose-ui-text")
    implementationLibrary("androidx-compose-foundation")
    implementationLibrary("androidx-navigation-compose")
    implementationLibrary("androidx-compose-material3")
    implementationLibrary("androidx-compose-runtime")
    implementationLibrary("androidx-compose-ui-tooling-preview")
    implementationLibrary("androidx-compose-ui-tooling")

    debugImplementationLibrary("androidx-compose-ui-tooling")
}
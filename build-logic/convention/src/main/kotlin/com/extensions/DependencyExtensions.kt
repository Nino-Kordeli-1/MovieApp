package com.extensions

import com.movieapp.libs
import org.gradle.api.Project

internal const val IMPLEMENTATION = "implementation"
internal const val API = "api"
internal const val TEST_IMPLEMENTATION = "testImplementation"
internal const val ANDROID_TEST_IMPLEMENTATION = "androidTestImplementation"
internal const val CORE_COMMON_MODULE = ":core:common"
internal const val CORE_DESIGN_SYSTEM_MODULE = ":core:designsystem"
internal const val CORE_UI_MODULE = ":core:ui"
internal const val CORE_DOMAIN_MODULE = ":core:domain"

fun Project.implementationModule(module: String) {
    dependencies.add(IMPLEMENTATION, project(module))
}

fun Project.apiModule(module: String) {
    dependencies.add(API, project(module))
}

fun Project.implementationLibrary(alias: String) {
    dependencies.add(IMPLEMENTATION, libs.findLibrary(alias).get())
}

fun Project.testImplementationLibrary(alias: String) {
    dependencies.add(TEST_IMPLEMENTATION, libs.findLibrary(alias).get())
}

fun Project.androidTestImplementationLibrary(alias: String) {
    dependencies.add(ANDROID_TEST_IMPLEMENTATION, libs.findLibrary(alias).get())
}

fun Project.debugImplementationLibrary(alias: String) {
    dependencies.add("debugImplementation", libs.findLibrary(alias).get())
}
import com.extensions.implementationLibrary
import org.gradle.api.Plugin
import org.gradle.api.Project

class KoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val hasAndroid = pluginManager.hasPlugin("com.android.base")

            implementationLibrary("koin-core")

            if (hasAndroid) {
                implementationLibrary("koin-android")
                implementationLibrary("koin-compose")
            }
        }
    }
}
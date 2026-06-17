import com.movieapp.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            val hasAndroid = pluginManager.hasPlugin("com.android.base")

            dependencies {
                add("implementation", libs.findLibrary("koin-core").get())

                if (hasAndroid) {
                    add("implementation", libs.findLibrary("koin-android").get())
                    add("implementation", libs.findLibrary("koin-compose").get())
                }
            }
        }
    }
}
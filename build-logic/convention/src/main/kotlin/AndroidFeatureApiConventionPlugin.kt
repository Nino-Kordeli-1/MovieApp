import com.extensions.apiModule
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureApiConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "movieapp.android.library")
            apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

            apiModule(":core:navigation")
        }
    }
}
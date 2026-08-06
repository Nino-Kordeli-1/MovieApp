import com.android.build.api.dsl.LibraryExtension
import com.extensions.CORE_COMMON_MODULE
import com.extensions.CORE_DESIGN_SYSTEM_MODULE
import com.extensions.CORE_DOMAIN_MODULE
import com.extensions.CORE_UI_MODULE
import com.extensions.implementationLibrary
import com.extensions.implementationModule
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class AndroidFeatureImplConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "movieapp.android.library")
            apply(plugin = "movieapp.koin.library")
            apply(plugin = "movieapp.android.compose")

            extensions.configure<LibraryExtension> {
                testOptions.animationsDisabled = true
            }

            implementationModule(CORE_COMMON_MODULE)
            implementationModule(CORE_DESIGN_SYSTEM_MODULE)
            implementationModule(CORE_UI_MODULE)
            implementationModule(CORE_DOMAIN_MODULE)

            implementationLibrary("androidx.lifecycle.runtimeCompose")
            implementationLibrary("androidx.lifecycle.viewModelCompose")
            implementationLibrary("androidx.navigation3.runtime")
            implementationLibrary("androidx-lifecycle-viewModel-navigation3")
            implementationLibrary("androidx.tracing.ktx")
            implementationLibrary("koin-android")
            implementationLibrary("koin-compose")
            implementationLibrary("coil-compose")
        }
    }
}
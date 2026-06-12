import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.example.movieapp.configureKotlinAndroid
import com.example.movieapp.disableUnnecessaryAndroidTests
import com.example.movieapp.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.library")
            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                testOptions.targetSdk = 36
                lint.targetSdk = 36
                defaultConfig.testInstrumentationRunner = "android.test.runner.AndroidJUnitRunner"
                testOptions.animationsDisabled = true
                resourcePrefix =
                    path.split("""\W""".toRegex()).drop(1).distinct().joinToString(separator = "_")
                        .lowercase() + "_"
            }
            extensions.configure<LibraryAndroidComponentsExtension>{
                disableUnnecessaryAndroidTests(target)
            }
            dependencies{
                "androidTestImplementation"(libs.findLibrary("kotlin.test").get())
                "testImplementation"(libs.findLibrary("kotlin.test").get())
                "testImplementation"(libs.findLibrary("junit").get())
                "implementation"(libs.findLibrary("androidx.tracing.ktx").get())
            }
        }
    }
}
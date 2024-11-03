import com.android.build.api.dsl.LibraryExtension
import com.example.cringehub.configureKotlin
import com.example.cringehub.configureKotlinAndroid
import com.example.cringehub.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.library")
            apply(plugin = "org.jetbrains.kotlin.android")

            extensions.configure<LibraryExtension> {
                testOptions.targetSdk = libs.findVersion("target-sdk").get().toString().toInt()
                defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                testOptions.animationsDisabled = true

                configureKotlin()
                configureKotlinAndroid(this)
            }

            dependencies {
                add("androidTestImplementation", kotlin("test"))
                add("testImplementation", kotlin("test"))
            }
        }
    }
}
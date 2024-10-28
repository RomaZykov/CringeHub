import com.android.build.api.dsl.ApplicationExtension
import com.example.cringehub.configureKotlin
import com.example.cringehub.configureKotlinAndroid
import com.example.cringehub.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

// Code related to android application logic e.g. minSdk, compileSdk, etc.
class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                defaultConfig.targetSdk = libs.findVersion("target-sdk").get().toString().toInt()
                buildFeatures.buildConfig = true

                configureKotlin()
                configureKotlinAndroid(this)
            }
        }
    }
}
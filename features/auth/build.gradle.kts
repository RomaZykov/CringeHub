plugins {
    alias(libs.plugins.cringehub.android.feature)
    alias(libs.plugins.cringehub.android.library.compose)
    alias(libs.plugins.cringehub.hilt)
    alias(libs.plugins.compose.screenshot)
}

android {
    namespace = "com.example.feature.auth"

    experimentalProperties["android.experimental.enableScreenshotTest"] = true

    testOptions {
        screenshotTests {
            imageDifferenceThreshold = 0.0001f // 0.01%
        }
    }
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:theme"))
    implementation(project(":core:common"))

    implementation(libs.androidx.ui.tooling.preview.android)
    implementation(libs.compose.material3)
    debugImplementation(libs.androidx.ui.tooling)

    androidTestImplementation(project(":core:test"))
    androidTestImplementation(libs.test.compose.ui.junit4.android)

    testImplementation(project(":core:test"))
}

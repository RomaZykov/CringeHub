plugins {
    alias(libs.plugins.cringehub.android.library)
    alias(libs.plugins.cringehub.android.library.compose)
    alias(libs.plugins.cringehub.android.feature)
    alias(libs.plugins.compose.screenshot)
}

android {
    namespace = "com.example.feature.onboarding"

    experimentalProperties["android.experimental.enableScreenshotTest"] = true

    testOptions {
        screenshotTests {
            imageDifferenceThreshold = 0.0001f // 0.01%
        }
    }
}

dependencies {
    implementation(libs.androidx.ui.tooling.preview.android)
    implementation(libs.androidx.material3.android)

    androidTestImplementation(libs.test.compose.ui.junit4)
    debugImplementation(libs.test.compose.ui.manifest)

    screenshotTestImplementation(libs.compose.ui.tooling)
    screenshotTestImplementation(platform(libs.compose.bom))
}
plugins {
    alias(libs.plugins.cringehub.android.library)
    alias(libs.plugins.cringehub.android.library.compose)
    alias(libs.plugins.cringehub.android.feature)
}

android {
    namespace = "com.example.feature.onboarding"
}

dependencies {
    implementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.ui.tooling.preview.android)

    androidTestImplementation(libs.test.compose.ui.junit4)
    debugImplementation(libs.test.compose.ui.manifest)

    screenshotTestImplementation(libs.compose.ui.tooling)
    screenshotTestImplementation(platform(libs.compose.bom))
}
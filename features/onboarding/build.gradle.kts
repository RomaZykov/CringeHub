plugins {
    alias(libs.plugins.cringehub.android.library)
    alias(libs.plugins.cringehub.android.library.compose)
    alias(libs.plugins.cringehub.android.feature)
    alias(libs.plugins.cringehub.hilt)
}

android {
    namespace = "com.example.feature.onboarding"
}

dependencies {
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.ui.tooling.preview.android)

    androidTestImplementation(libs.test.compose.ui.junit4)
}
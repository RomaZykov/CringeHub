plugins {
    alias(libs.plugins.cringehub.android.library)
    alias(libs.plugins.cringehub.android.library.compose)
    alias(libs.plugins.cringehub.android.feature)
    alias(libs.plugins.cringehub.hilt)
}

android {
    namespace = "com.example.auth"
}

dependencies {
    implementation(project(":core:domain"))

    implementation(libs.androidx.material3.android)
    debugImplementation(libs.androidx.ui.tooling)
    implementation(libs.androidx.ui.tooling.preview.android)

    testImplementation(project(":core:test"))
    androidTestImplementation(project(":core:test"))
    androidTestImplementation(libs.test.compose.ui.junit4)
}

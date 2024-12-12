plugins {
    alias(libs.plugins.cringehub.android.library)
    alias(libs.plugins.cringehub.android.library.compose)
    alias(libs.plugins.cringehub.android.feature)
    alias(libs.plugins.cringehub.hilt)
}

android {
    namespace = "com.example.feature.auth"
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:theme"))

    implementation(libs.androidx.ui.tooling.preview.android)
    implementation(libs.compose.material3)
    debugImplementation(libs.androidx.ui.tooling)

    testImplementation(project(":core:test"))
    androidTestImplementation(project(":core:test"))
    androidTestImplementation(libs.test.compose.ui.junit4)
}

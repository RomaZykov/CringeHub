plugins {
    alias(libs.plugins.cringehub.android.feature)
    alias(libs.plugins.cringehub.android.library.compose)
}

android {
    namespace = "com.example.adminhome"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:adminNavigation"))
    implementation(project(":core:domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.ui.tooling.preview.android)
    implementation(libs.compose.material3)

    debugImplementation(libs.androidx.ui.tooling)

    androidTestImplementation(project(":core:test"))
    androidTestImplementation(libs.test.compose.ui.junit4.android)
    androidTestImplementation(libs.test.junit)
    androidTestImplementation(libs.compose.navigation.testing)

    testImplementation(libs.junit)
}
plugins {
    alias(libs.plugins.cringehub.android.library)
    alias(libs.plugins.cringehub.android.library.compose)
    alias(libs.plugins.cringehub.hilt)
}

android {
    namespace = "com.example.adminnavigation"
}

dependencies {
    implementation(libs.compose.navigation)
}
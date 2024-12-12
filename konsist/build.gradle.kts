plugins {
    alias(libs.plugins.cringehub.android.library)
}

android {
    namespace = "com.example.konsist"
}

dependencies {
    testImplementation(libs.androidx.lifecycle.viewmodel.android)
    testImplementation(libs.compose.ui.tooling.preview)
    testImplementation(libs.konsist)
}
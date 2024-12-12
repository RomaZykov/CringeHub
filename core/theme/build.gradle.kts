plugins {
    alias(libs.plugins.cringehub.android.library)
    alias(libs.plugins.compose)
}

android {
    namespace = "com.example.cringehub.theme"
}
dependencies {
    api(libs.compose.ui)
    api(libs.compose.ui.graphics)
    api(libs.compose.ui.text.android)
    api(libs.compose.material3)
    api(libs.compose.material3.android)
    api(libs.compose.runtime.android)
    api(libs.compose.foundation.android)
}

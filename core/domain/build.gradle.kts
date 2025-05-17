plugins {
    alias(libs.plugins.cringehub.android.library)
    alias(libs.plugins.cringehub.hilt)
}

android {
    namespace = "com.example.domain"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
}

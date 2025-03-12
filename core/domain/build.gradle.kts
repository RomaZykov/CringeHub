plugins {
    alias(libs.plugins.cringehub.android.library)
}

android {
    namespace = "com.example.domain"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
}

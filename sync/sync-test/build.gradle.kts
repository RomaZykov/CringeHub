plugins {
    alias(libs.plugins.cringehub.android.library)
    alias(libs.plugins.cringehub.hilt)
}

android {
    namespace = "com.example.sync_test"
}

dependencies {
    androidTestImplementation(libs.androidx.work.testing)
}
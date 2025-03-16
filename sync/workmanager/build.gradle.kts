plugins {
    alias(libs.plugins.cringehub.android.library)
    alias(libs.plugins.cringehub.hilt)
}

android {
    namespace = "com.example.workmanager"
}

dependencies {
    implementation(libs.androidx.work.runtime.ktx)
}
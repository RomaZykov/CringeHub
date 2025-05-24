plugins {
    alias(libs.plugins.cringehub.android.library)
    alias(libs.plugins.cringehub.firebase)
    alias(libs.plugins.cringehub.hilt)
}

android {
    namespace = "com.example.network"
}

dependencies {
    implementation(project(":core:common"))

    implementation(libs.androidx.work.runtime.ktx)
}


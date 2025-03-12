plugins {
    alias(libs.plugins.cringehub.android.library)
    alias(libs.plugins.cringehub.hilt)
}

android {
    namespace = "com.example.database"
}

dependencies {
    implementation(libs.androidx.room)
    implementation(libs.androidx.core.ktx)

    testImplementation(libs.hilt.android.testing)
}
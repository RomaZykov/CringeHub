plugins {
    alias(libs.plugins.cringehub.android.library)
    alias(libs.plugins.cringehub.hilt)
}

android {
    namespace = "com.example.workmanager"
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:data"))

    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.firebase.firestore)
    implementation(libs.androidx.hilt.common)

    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.androidx.work.testing)
}
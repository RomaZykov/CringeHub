plugins {
    alias(libs.plugins.cringehub.android.library)
    alias(libs.plugins.cringehub.hilt)
}

android {
    namespace = "com.example.database"
}

dependencies {
    implementation(project(":core:common"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.room)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.test.runner)
}
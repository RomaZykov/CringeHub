plugins {
    alias(libs.plugins.cringehub.android.library)
    alias(libs.plugins.cringehub.hilt)
}

android {
    namespace = "com.example.test"
}

dependencies {
    implementation(project(":core:domain"))

    implementation(libs.hilt.android.testing)
    implementation(libs.test.runner)
    implementation(libs.test.rules)
}

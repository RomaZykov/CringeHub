plugins {
    alias(libs.plugins.cringehub.android.library)
}

android {
    namespace = "com.example.konsist"
}

dependencies {
    implementation(libs.androidx.lifecycle.viewmodel.android)
    testImplementation(libs.konsist)
}
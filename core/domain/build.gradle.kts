plugins {
    alias(libs.plugins.cringehub.android.library)
    alias(libs.plugins.cringehub.firebase)
}

android {
    namespace = "com.example.domain"
}
dependencies {
    implementation(libs.play.services.auth)
    implementation(libs.androidx.credentials)
}

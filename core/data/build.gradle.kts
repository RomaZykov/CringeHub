plugins {
    alias(libs.plugins.cringehub.android.library)
    alias(libs.plugins.cringehub.hilt)
    alias(libs.plugins.cringehub.firebase)
}

android {
    namespace = "com.example.data"

    dependencies {
        implementation(project(":core:domain"))
        implementation(project(":core:common"))

        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.appcompat)

        implementation(libs.androidx.credentials)
        implementation(libs.androidx.credentials.play.services.auth)
        implementation(libs.googleid)
    }
}
plugins {
    alias(libs.plugins.cringehub.android.library)
    alias(libs.plugins.cringehub.firebase)
}

android {
    namespace = "com.example.data"

    dependencies {
        implementation(project(":core:domain"))

        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.appcompat)

        implementation(libs.androidx.credentials)
        implementation(libs.googleid)
    }
}
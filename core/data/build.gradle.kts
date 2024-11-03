plugins {
    alias(libs.plugins.cringehub.android.library)
    alias(libs.plugins.gms)
}
android {
    namespace = "com.example.data"

    dependencies {
        implementation(project(":core:domain"))

        androidTestImplementation(libs.test.compose.ui.junit4)

        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.appcompat)

        // Firebase
        implementation(platform(libs.firebase.bom))
        implementation(libs.firebase.common.ktx)
        implementation(libs.firebase.auth)
        implementation(libs.play.services.auth)

        implementation(libs.androidx.credentials)
        implementation(libs.androidx.credentials.play.services.auth)
        implementation(libs.googleid)
    }
}
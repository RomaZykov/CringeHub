plugins {
    alias(libs.plugins.cringehub.android.library)
    alias(libs.plugins.cringehub.hilt)
    alias(libs.plugins.cringehub.firebase)
}

android {
    namespace = "com.example.data"
}

dependencies {
    api(project(":core:common"))
    api(project(":core:domain"))
    api(project(":core:database"))
    api(project(":core:network"))
    api(project(":core:test"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.hilt.android.testing)

    // Auth
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)

    testImplementation(platform(libs.test.junit.bom))
    testImplementation(libs.test.junit.jupiter)
}

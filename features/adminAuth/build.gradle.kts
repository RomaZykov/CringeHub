plugins {
    alias(libs.plugins.cringehub.android.feature)
    alias(libs.plugins.cringehub.android.library.compose)
}

android {
    namespace = "com.example.adminauth"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:adminNavigation"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    testImplementation(libs.junit)

    androidTestImplementation(libs.test.junit.ktx)
}
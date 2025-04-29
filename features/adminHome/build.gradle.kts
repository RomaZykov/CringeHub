plugins {
    alias(libs.plugins.cringehub.android.feature)
    alias(libs.plugins.cringehub.android.library.compose)
}

android {
    namespace = "com.example.adminhome"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:adminNavigation"))
    implementation(project(":core:domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.ui.tooling.preview.android)
    implementation(libs.compose.material3)
    implementation(libs.test.junit.ktx)

    debugImplementation(libs.androidx.ui.tooling)

    implementation(libs.compose.navigation.testing)

    androidTestImplementation(project(":core:test"))
    androidTestImplementation(libs.test.core.ktx)
    androidTestImplementation(libs.test.compose.ui.junit4.android)
    androidTestImplementation(libs.test.junit)

    testImplementation(project(":core:test"))
    testImplementation(libs.junit)
    testImplementation(libs.test.junit.jupiter)
    testImplementation(libs.kotlinx.coroutines.test)
}
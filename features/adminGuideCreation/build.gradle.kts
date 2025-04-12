plugins {
    alias(libs.plugins.cringehub.android.feature)
    alias(libs.plugins.cringehub.android.library.compose)
    alias(libs.plugins.cringehub.hilt)
}

android {
    namespace = "com.example.adminguidecreation"

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:theme"))
    implementation(project(":core:domain"))
    implementation(project(":core:adminNavigation"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.ui.tooling.preview.android)
    implementation(libs.compose.material3)
    // Rich Text Editor
    implementation(libs.richeditor.compose)
    implementation(libs.androidx.junit.ktx)

//    kspTest(libs.hilt.compiler)

    androidTestImplementation(project(":core:test"))
    androidTestImplementation(libs.test.junit)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.test.compose.ui.junit4)
    debugImplementation(libs.test.compose.ui.manifest)

    testImplementation(libs.junit)
    testImplementation(libs.test.junit.jupiter)
    testImplementation(libs.kotlinx.coroutines.test)

    debugImplementation(libs.androidx.ui.tooling)
}
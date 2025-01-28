plugins {
    alias(libs.plugins.cringehub.android.library.compose)
    alias(libs.plugins.cringehub.android.feature)
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
    implementation(project(":core:test"))
    implementation(project(":core:common"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    // Rich Text Editor lib
    implementation(libs.richeditor.compose)

    testImplementation(libs.junit)

    androidTestImplementation(libs.test.junit)
    androidTestImplementation(libs.test.espresso.core)
    androidTestImplementation(libs.test.compose.ui.junit4)
    androidTestImplementation(libs.hilt.android.testing)
}
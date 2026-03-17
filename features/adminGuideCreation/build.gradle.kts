plugins {
    alias(libs.plugins.cringehub.android.feature)
    alias(libs.plugins.cringehub.android.library.compose)
    alias(libs.plugins.cringehub.hilt)
}

android {
    namespace = "com.example.adminGuideCreation"

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:theme"))
    implementation(project(":core:ui"))
    implementation(project(":core:domain"))
    implementation(project(":core:adminNavigation"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.ui.tooling.preview.android)
    implementation(libs.compose.material3)
    implementation(libs.test.junit.ktx)
    // Glide
    implementation(libs.glide)
    implementation(libs.glide.compose)
    implementation(libs.androidx.runtime)
    implementation(libs.androidx.material3)

    androidTestImplementation(project(":core:test"))
    androidTestImplementation(libs.test.junit.ktx)
    androidTestImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.test.compose.ui.junit4.android)

    debugImplementation(libs.androidx.ui.tooling)

    testImplementation(project(":core:test"))
    testImplementation(libs.junit)
    testImplementation(libs.test.junit.jupiter)
    testImplementation(libs.kotlinx.coroutines.test)
}
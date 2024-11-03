plugins {
    alias(libs.plugins.cringehub.android.library)
}

android {
    namespace = "com.example.ui"

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
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    testImplementation(libs.test.junit)
    androidTestImplementation(libs.test.junit)
    androidTestImplementation(libs.test.espresso.core)
}
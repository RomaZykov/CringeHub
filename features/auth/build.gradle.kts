plugins {
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}
android {
    namespace = "com.example.auth"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }
    signingConfigs {
        getByName("debug") {
            storeFile = file("$storeFile")
            storePassword = "android"
            keyAlias = "debug"
            keyPassword = "android"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.navigation.compose)

    // Auth
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.play.services.auth)

    // Hilt
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.android.testing)

    implementation(libs.androidx.junit.ktx)
}

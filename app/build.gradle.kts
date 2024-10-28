plugins {
    alias(libs.plugins.cringehub.android.application)

    alias(libs.plugins.compose.compiler)

    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.cringehub"

    signingConfigs {
        getByName("debug") {
            storeFile = file("$storeFile")
            storePassword = "android"
            keyAlias = "debug"
            keyPassword = "android"
        }
        create("release") {
//            try {
//                storeFile = file("$storeFile")
//                storePassword = KEYSTORE_PASSWORD
//                keyAlias = "release"
//                keyPassword =  KEY_PASSWORD
//            } catch (ex) {
//                throw InvalidUserDataException("You should define KEYSTORE_PASSWORD and KEY_PASSWORD in gradle.properties.")
//            }
        }
    }

    defaultConfig {
        applicationId = "com.example.cringehub"
        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
//            buildConfigField "String", "BASE_URL", '"https://moguchi-app-default-rtdb.europe-west1.firebasedatabase.app"'
//            buildConfigField "String", "SERVER_CLIENT_ID", '"620122652964-ruvd9u3fru7mqfpdh9j3sd13i7b71lem.apps.googleusercontent.com"'
        }

        debug {
            applicationIdSuffix = ".debug"
//            buildConfigField "String", "BASE_URL", '"https://moguchi-debug-default-rtdb.europe-west1.firebasedatabase.app"'
//            buildConfigField "String", "SERVER_CLIENT_ID", '"620122652964-ruvd9u3fru7mqfpdh9j3sd13i7b71lem.apps.googleusercontent.com"'
            signingConfig = signingConfigs.getByName("debug")
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":features:auth"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)

    testImplementation(libs.junit)
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)
    androidTestImplementation(libs.androidx.navigation.testing)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    ksp(libs.androidx.room.compiler)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.kotlinx.serialization.json)

    // Hilt
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    androidTestImplementation(libs.hilt.android.testing)
}

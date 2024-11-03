plugins {
    alias(libs.plugins.cringehub.android.application)
    alias(libs.plugins.cringehub.android.application.compose)
    alias(libs.plugins.cringehub.hilt)
    alias(libs.plugins.kotlin.serialization)
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
    implementation(project(":core:data"))
    implementation(project(":features:auth"))

    implementation(platform(libs.compose.bom))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.compose.activity)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.compose.navigation)

    testImplementation(platform(libs.test.junit.bom))
    testImplementation(libs.test.junit.jupiter)
    testImplementation(libs.test.junit)
    testRuntimeOnly(libs.test.junit.platform.launcher)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.navigation.testing)
    androidTestImplementation(libs.test.junit)
    androidTestImplementation(libs.test.espresso.core)
    androidTestImplementation(libs.test.compose.ui.junit4)
    ksp(libs.androidx.room.compiler)

    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.test.compose.ui.manifest)

    implementation(libs.kotlinx.serialization.json)

    androidTestImplementation(libs.hilt.android.testing)
}

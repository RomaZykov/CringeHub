plugins {
    alias(libs.plugins.cringehub.android.application)
    alias(libs.plugins.cringehub.android.application.compose)
    alias(libs.plugins.cringehub.hilt)
    alias(libs.plugins.cringehub.firebase)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.gms)
}

android {
    namespace = "com.example.cringehub"

    signingConfigs {
        getByName("debug") {
            storeFile = file("$storeFile")
            storePassword = "android"
            keyPassword = "android"
            keyAlias = "AndroidDebugKey"
        }
        create("release") {
            try {
                storeFile = file("$storeFile")
                storePassword = providers.gradleProperty("KEYSTORE_PASSWORD").get()
                keyAlias = "release"
                keyPassword = providers.gradleProperty("KEY_PASSWORD").get()
            } catch (e: Exception) {
                throw InvalidUserDataException("You should define KEYSTORE_PASSWORD and KEY_PASSWORD in gradle.properties.")
            }
        }
    }

    defaultConfig {
        applicationId = "com.example.cringehub"
        versionCode = 1
        versionName = "1.0"
        vectorDrawables {
            useSupportLibrary = true
        }
        signingConfig = signingConfigs.getByName("debug")

        testInstrumentationRunner = "com.example.test.CringeHubTestRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }

        debug {
            applicationIdSuffix = ".debug"
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
    implementation(project(":core:common"))
    implementation(project(":core:theme"))

    implementation(project(":features:auth"))
    implementation(project(":features:onboarding"))
    implementation(project(":features:hub"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.firebase.firestore)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.activity)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.navigation)

    testRuntimeOnly(libs.test.junit.platform.launcher)
    testImplementation(platform(libs.test.junit.bom))
    testImplementation(libs.test.junit.jupiter)
    testImplementation(libs.test.junit)

    androidTestImplementation(project(":core:test"))
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.compose.navigation.testing)
    androidTestImplementation(libs.test.junit)
    androidTestImplementation(libs.test.compose.ui.junit4.android)
    androidTestImplementation(libs.hilt.android.testing)

    ksp(libs.androidx.room.compiler)

    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.test.compose.ui.manifest)
}

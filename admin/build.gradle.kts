plugins {
    alias(libs.plugins.cringehub.android.application)
    alias(libs.plugins.compose)
    alias(libs.plugins.cringehub.hilt)
    alias(libs.plugins.cringehub.firebase)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.gms)
}

android {
    namespace = "com.example.cringehub.admin"

    defaultConfig {
        applicationId = "com.example.cringehub.admin"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.example.test.CringeHubTestRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
    implementation(project(":core:adminNavigation"))
    implementation(project(":core:theme"))

    implementation(project(":features:adminAuth"))
    implementation(project(":features:adminHome"))
    implementation(project(":features:adminGuideCreation"))

    implementation(project(":sync:workmanager"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.compose.activity)
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3.android)
    implementation(libs.compose.navigation.testing)
    implementation(libs.androidx.hilt.work)

    testImplementation(libs.junit)

    androidTestImplementation(project(":core:test"))
    androidTestImplementation(libs.test.junit)
    androidTestImplementation(libs.test.runner)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.test.compose.ui.junit4)
    androidTestImplementation(libs.hilt.android.testing)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.test.compose.ui.manifest)
}
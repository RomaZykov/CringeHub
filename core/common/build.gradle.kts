plugins {
    alias(libs.plugins.cringehub.android.library)
}

android {

    namespace = "com.example.common"

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField(
                "String",
                "BASE_URL",
                "\"BASE_URL_RELEASE_FROM_COMMON\""
            )
            buildConfigField(
                "String",
                "SERVER_CLIENT_ID",
                "\"SERVER_CLIENT_ID_RELEASE_FROM_COMMON\""
            )
        }

        debug {
            buildConfigField(
                "String",
                "BASE_URL",
                "\"BASE_URL_DEBUG_FROM_COMMON\""
            )
            buildConfigField(
                "String",
                "SERVER_CLIENT_ID",
                "\"SERVER_CLIENT_ID_DEBUG_FROM_COMMON\""
            )
        }
    }
}

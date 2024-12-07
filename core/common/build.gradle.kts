plugins {
    alias(libs.plugins.cringehub.android.library)
    alias(libs.plugins.cringehub.hilt)
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
                "SERVER_CLIENT_ID",
                "\"172651466072-c84a39e1n6ghrd61ppiue03ii1da33hp.apps.googleusercontent.com\""
            )
        }

        debug {
            buildConfigField(
                "String",
                "SERVER_CLIENT_ID",
                "\"172651466072-c84a39e1n6ghrd61ppiue03ii1da33hp.apps.googleusercontent.com\""
            )
        }
    }
}

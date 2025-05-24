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
            // for Auth
            buildConfigField(
                "String",
                "SERVER_CLIENT_ID",
                "\"172651466072-c84a39e1n6ghrd61ppiue03ii1da33hp.apps.googleusercontent.com\""
            )
            // for Admin database
            buildConfigField(
                "String",
                "SERVER_ADMIN_ID",
                "\"\""
            )
        }

        debug {
            // for Auth
            buildConfigField(
                "String",
                "SERVER_CLIENT_ID",
                "\"172651466072-c84a39e1n6ghrd61ppiue03ii1da33hp.apps.googleusercontent.com\""
            )
            // for Admin database
            buildConfigField(
                "String",
                "SERVER_ADMIN_ID",
                "\"\""
            )
        }
    }
}

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
                "SERVER_CLIENT_ID",
                "\"SERVER_CLIENT_ID_RELEASE_FROM_COMMON\""
            )
        }

        debug {
            buildConfigField(
                "String",
                "SERVER_CLIENT_ID",
                "\"753665239042-v5gfiv9l2thti048a1om9sj57p4tnnbq.apps.googleusercontent.com\""
            )
        }
    }
}

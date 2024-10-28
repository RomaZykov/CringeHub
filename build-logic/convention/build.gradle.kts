plugins {
    `kotlin-dsl`
}

group = "com.example.cringehub"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(libs.versions.jvm.get()))
    }
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "cringehub.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
//        register("hilt") {
//            id = "cringehub.hilt"
//            implementationClass = "HiltConventionPlugin"
//        }
    }
}
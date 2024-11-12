plugins {
    alias(libs.plugins.cringehub.android.library)
}

android {
    namespace = "com.example.lint"
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dependencies {
    testImplementation(libs.konsist)
    testImplementation(libs.kotest.runner.junit5)
}
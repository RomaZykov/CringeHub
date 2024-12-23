// Workaround for issue https://stackoverflow.com/questions/77279080/unable-to-make-progress-running-work-android-studio
gradle.startParameter.excludedTaskNames.addAll(listOf(":build-logic:convention:testClasses"))

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "cringehub"
include(":app")
include(":core:domain")
include(":core:data")
include(":core:ui")
include(":core:test")
include(":core:database")
include(":core:network")
include(":core:common")
include(":features:auth")
include(":konsist")
include(":features:onboarding")
include(":core:theme")

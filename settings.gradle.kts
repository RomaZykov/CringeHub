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
include(":admin")
include(":core:domain")
include(":core:data")
include(":core:ui")
include(":core:test")
include(":core:database")
include(":core:network")
include(":core:common")
include(":core:theme")
include(":features:auth")
include(":features:onboarding")
include(":features:adminAuth")
include(":features:adminHome")
include(":features:hub")
include(":features:seasonPass")
include(":features:store")
include(":konsist")

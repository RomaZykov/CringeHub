// Workaround for issue https://stackoverflow.com/questions/77279080/unable-to-make-progress-running-work-android-studio
//gradle.startParameter.excludedTaskNames.addAll(listOf(":build-logic:convention:testClasses"))

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
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.10.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "CringeHub"
include(":app")
include(":admin")

include(":core:domain")
include(":core:data")
include(":core:test")
include(":core:database")
include(":core:network")
include(":core:common")
include(":core:theme")
include(":core:adminNavigation")

include(":features:auth")
include(":features:onboarding")
include(":features:hub")
include(":features:store")
include(":features:adminGuideCreation")
include(":features:adminAuth")
include(":features:adminHome")

include(":konsist")
include(":sync:sync-test")
include(":sync:workmanager")
include(":ui-test-hilt-manifest")

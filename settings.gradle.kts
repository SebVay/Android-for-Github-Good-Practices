pluginManagement {

    // Centralize the common build logic inside the app
    includeBuild("config/build-logic")

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

rootProject.name = "Github App"
include(":app")
include(":data-apollo")
include(":data")
include(":domain")
include(":domain-contract")

// Core modules
include(":core:viewmodel")
include(":core:ui:navigation")
include(":core:ui:theme")
include(":core:ui:component")

// UI Modules
include(":ui:repo")

// Others
include(":config:lint-rules")
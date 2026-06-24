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

rootProject.name = "MovieApp"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")

//Core
include(":core:model")
include(":core:domain")
include(":core:common")
include(":core:data")
include(":core:navigation")
include(":core:designsystem")
include(":core:ui")
include(":core:network")

//Feature
include(":feature:home")
include(":feature:home:api")
include(":feature:home:impl")

include(":feature:details")
include(":feature:details:api")
include(":feature:details:impl")

include(":feature:favorites")
include(":feature:favorites:impl")
include(":feature:favorites:api")

include(":feature:splash")
include(":feature:splash:impl")
include(":feature:splash:api")

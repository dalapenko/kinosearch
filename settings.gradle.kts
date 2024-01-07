pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "KinoSearch"
include(":app")
include(":feature:premieres")
include(":feature:releases")
include(":feature:search")
include(":feature:filmdetails")
include(":data:core")
include(":data:releases")
include(":core:network")
include(":core:database")

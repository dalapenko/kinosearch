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
include(":core:network")
include(":core:database")
include(":core:basepresentation")
include(":data:releases")
include(":data:premieres")
include(":data:search")
include(":data:filmdetails")
include(":data:network")
include(":feature:premieres")
include(":feature:releases")
include(":feature:search")
include(":feature:filmdetails")

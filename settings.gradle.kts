pluginManagement {
    repositories {
        mavenCentral() // Maven 중앙 레포지토리
        google()
        mavenCentral()
        maven {
            url = uri("https://repo.eclipse.org/content/repositories/paho-releases/")
        }
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

rootProject.name = "Capstone"
include(":app")
 
rootProject.name = "JalaliDatePickerKMM"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven {
            name = "gitHubPackages"
            url = uri("https://maven.pkg.github.com/MohammadRezaei92/JalaliDatePickerComposeKmm")
            credentials(PasswordCredentials::class)
        }
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        maven {
            name = "gitHubPackages"
            url = uri("https://maven.pkg.github.com/MohammadRezaei92/JalaliDatePickerComposeKmm")
            credentials(PasswordCredentials::class)
        }
    }
}

include(":composeApp")
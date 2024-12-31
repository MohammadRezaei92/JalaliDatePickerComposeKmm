import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.publishMaven)
}

kotlin {
    androidTarget {
        publishLibraryVariants("release")
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
        publishLibraryVariants("release", "debug")
    }


    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            freeCompilerArgs += listOf("-Xbinary=bundleId=rezaei.mohammad.jalalidatepickerkmm")
        }
    }
    
    sourceSets {
        
        androidMain.dependencies {
            implementation(compose.preview)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            api(libs.kotlinx.datetime)
            implementation(libs.kotlin.stdlib.common)
        }
    }
}

android {
    namespace = "rezaei.mohammad.jalalidatepickerkmm"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()

    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
    lint {
        baseline = file("lint-baseline.xml")
        abortOnError = false
    }
}

compose.resources {
    generateResClass = always
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url =
                uri("https://maven.pkg.github.com/MohammadRezaei92/JalaliDatePickerComposeKmm")
            credentials(PasswordCredentials::class)
        }
    }
}

mavenPublishing {
    // Define coordinates for the published artifact
    coordinates(
        groupId = "rezaei.mohammad",
        artifactId = "jalalidatepickerkmm",
        version = "1.0.0"
    )

    // Configure POM metadata for the published artifact
    pom {
        name.set("Jalali DataPicker KMM")
        description.set("Compose multiplatform jalali date picker")
        inceptionYear.set("2024")
        url.set("https://github.com/MohammadRezaei92/JalaliDatePickerComposeKmm")

        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }

        // Specify developers information
        developers {
            developer {
                id.set("<GITHUB_USER_NAME>")
                name.set("<GITHUB_ACTUAL_NAME>")
                email.set("<GITHUB_EMAIL_ADDRESS>")
            }
        }

        // Specify SCM information
        scm {
            url.set("https://github.com/<GITHUB_USER_NAME>/MathLibGuide")
        }
    }
}

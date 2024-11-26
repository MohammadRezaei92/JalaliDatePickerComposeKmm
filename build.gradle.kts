plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
}

tasks.register<Zip>("zipIosArm64Framework") {
    group = "build"
    description = "Zips the iOS Arm64 framework for local use"
    from(layout.buildDirectory.dir("bin/iosArm64/releaseFramework/ComposeApp.framework"))
    destinationDirectory.set(file("$buildDir/outputs/iosFrameworks"))
    archiveFileName.set("ComposeApp-iosArm64.framework.zip")
}

tasks.register<Zip>("zipIosX64Framework") {
    group = "build"
    description = "Zips the iOS X64 framework for local use"
    from(layout.buildDirectory.dir("bin/iosX64/releaseFramework/ComposeApp.framework"))
    destinationDirectory.set(file(layout.buildDirectory.dir("outputs/iosFrameworks")))
    archiveFileName.set("ComposeApp-iosX64.framework.zip")
}

tasks.register<Zip>("zipIosSimulatorArm64Framework") {
    group = "build"
    description = "Zips the iOS Simulator Arm64 framework for local use"
    from(layout.buildDirectory.dir("bin/iosSimulatorArm64/releaseFramework/ComposeApp.framework"))
    destinationDirectory.set(file(layout.buildDirectory.dir("outputs/iosFrameworks")))
    archiveFileName.set("ComposeApp-iosSimulatorArm64.framework.zip")
}


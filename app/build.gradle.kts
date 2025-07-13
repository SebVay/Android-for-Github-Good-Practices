plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.convention.config)
    alias(libs.plugins.convention.dependencies)
    alias(libs.plugins.convention.compose)
}

android {
    namespace = "com.github.app"

    defaultConfig {
        applicationId = "com.github.app"
        targetSdk =
            libs.versions.androidTargetSdk
                .get()
                .toInt()
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        debug {
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-DEBUG"
        }

        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))

    // Core Modules
    implementation(project(":core:ui:theme"))
    implementation(project(":core:ui:navigation"))

    // Ui Modules
    implementation(project(":ui:repo"))

    // Material Library for theming in the Manifest
    implementation(libs.material)
}

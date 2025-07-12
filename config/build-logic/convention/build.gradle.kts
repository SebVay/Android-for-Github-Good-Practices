plugins {
    `kotlin-dsl`
}

group = "com.github.app.buildLogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.build.tools)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.spotless.gradle)
    compileOnly(libs.detekt.gradle)
}

gradlePlugin {
    plugins {
        register("androidCommonConfig") {
            id = libs.plugins.common.config.convention.get().pluginId
            implementationClass = "AndroidCommonConfigConventionPlugin"
        }

        register("androidCommonDependencies") {
            id = libs.plugins.common.dependencies.convention.get().pluginId
            implementationClass = "AndroidCommonDependenciesConventionPlugin"
        }

        register("androidCommonCompose") {
            id = libs.plugins.common.compose.convention.get().pluginId
            implementationClass = "AndroidCommonComposeConventionPlugin"
        }

        register("androidCommonUi") {
            id = libs.plugins.common.ui.convention.get().pluginId
            implementationClass = "AndroidCommonUiConventionPlugin"
        }
    }
}
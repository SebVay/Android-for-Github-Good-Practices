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
        register("androidConfigPlugin") {
            id = libs.plugins.convention.config.get().pluginId
            implementationClass = "AndroidConfigConventionPlugin"
        }

        register("androidDependenciesPlugin") {
            id = libs.plugins.convention.dependencies.get().pluginId
            implementationClass = "AndroidDependenciesConventionPlugin"
        }

        register("androidComposePlugin") {
            id = libs.plugins.convention.compose.get().pluginId
            implementationClass = "AndroidComposeConventionPlugin"
        }

        register("androidUiPlugin") {
            id = libs.plugins.convention.ui.module.get().pluginId
            implementationClass = "AndroidUiConventionPlugin"
        }
        register("codeQualityPlugin") {
            id = libs.plugins.convention.code.quality.get().pluginId
            implementationClass = "CodeQualityConventionPlugin"
        }
    }
}
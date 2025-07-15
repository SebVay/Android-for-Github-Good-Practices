plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.convention.config)
    alias(libs.plugins.convention.dependencies)
}

android {
    namespace = "com.github.app.domain"
}

kotlin {
    compilerOptions {
        optIn.add("kotlin.time.ExperimentalTime")
    }
}

dependencies {
    implementation(project(":domain-contract"))
    implementation(libs.kotlinx.datetime)
}

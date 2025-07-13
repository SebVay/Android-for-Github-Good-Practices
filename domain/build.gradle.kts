plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.convention.config)
    alias(libs.plugins.convention.dependencies)
}

android {
    namespace = "com.github.app.domain"
}

dependencies {
    implementation(project(":domain-contract"))
}

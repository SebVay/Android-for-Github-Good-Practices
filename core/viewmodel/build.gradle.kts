plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.convention.config)
    alias(libs.plugins.convention.dependencies)
    alias(libs.plugins.convention.compose)
}

android {
    namespace = "com.github.app.core.viewmodel"
}

dependencies {
    implementation(project(":core:ui:theme"))
    implementation(project(":core:ui:navigation"))
}

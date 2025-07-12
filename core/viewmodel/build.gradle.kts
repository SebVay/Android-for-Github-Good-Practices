plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.common.config.convention)
    alias(libs.plugins.common.dependencies.convention)
    alias(libs.plugins.common.compose.convention)
}

android {
    namespace = "com.github.app.core.viewmodel"
}

dependencies {
    implementation(project(":core:ui:theme"))
    implementation(project(":core:ui:navigation"))
}

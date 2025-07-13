plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.convention.config)
    alias(libs.plugins.convention.dependencies)
    alias(libs.plugins.convention.compose)
}

android {
    namespace = "com.github.app.core.ui.theme"
}

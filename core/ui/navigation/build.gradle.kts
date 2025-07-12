plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.common.config.convention)
    alias(libs.plugins.common.compose.convention)
    alias(libs.plugins.common.dependencies.convention)
}

android {
    namespace = "com.github.app.core.ui.navigation"
}

plugins {
    alias(libs.plugins.android.library)

    // All the configuration & magic is included in this convention plugin
    alias(libs.plugins.common.ui.convention)
}

android {
    namespace = "com.github.app.ui.repo"
}

dependencies {
    implementation(project(":domain"))
}

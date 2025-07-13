plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.convention.ui.module)
}

android {
    namespace = "com.github.app.ui.repo"
}

dependencies {
    implementation(project(":domain"))
}

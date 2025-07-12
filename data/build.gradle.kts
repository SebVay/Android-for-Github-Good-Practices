plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.common.config.convention)
    alias(libs.plugins.common.dependencies.convention)
}

android {
    namespace = "com.github.app.data"
}

dependencies {
    implementation(project(":domain-contract"))
    implementation(project(":data-apollo"))
}

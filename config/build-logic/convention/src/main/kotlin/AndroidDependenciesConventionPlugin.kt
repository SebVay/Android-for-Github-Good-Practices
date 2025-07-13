import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * A convention plugin that applies common dependencies used by Android modules (Application and Library).
 * This prevents duplication across multiple modules.
 *
 * Inspired by:
 * - [Home Assistant repo](https://github.com/home-assistant/android/tree/main/build-logic)
 * - [An interesting Medium article](https://medium.com/@sridhar-sp/simplify-your-android-builds-a-guide-to-convention-plugins-b9fea8c5e117)
 */
class AndroidDependenciesConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            androidConfig {
                dependencies {
                    "implementation"(libs.findLibrary("timber").get())
                    "implementation"(libs.findLibrary("koin").get())

                    "lintChecks"(project(":config:lint-rules"))

                    "testImplementation"(platform(libs.findLibrary("junit-bom").get()))
                    "testImplementation"(libs.findLibrary("junit-jupiter").get())
                    "testImplementation"(libs.findLibrary("mockk").get())
                }
            }
        }
    }
}
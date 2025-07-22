import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * A convention plugin that applies the compose configurations needed by Android modules (Application and Library).
 * This prevents duplication across multiple modules.
 *
 * Inspired by:
 * - [Home Assistant repo](https://github.com/home-assistant/android/tree/main/build-logic)
 * - [An interesting Medium article](https://medium.com/@sridhar-sp/simplify-your-android-builds-a-guide-to-convention-plugins-b9fea8c5e117)
 */
class AndroidComposeConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            apply(plugin = libs.findPluginId("kotlin-compose"))

            androidConfig {
                buildFeatures {
                    compose = true
                }

                dependencies {

                    val composeBom = platform(libs.findLibrary("androidx-compose-bom").get())

                    "androidTestImplementation"(composeBom)
                    "implementation"(composeBom)

                    "implementation"(libs.findLibrary("androidx-core-ktx").get())
                    "implementation"(libs.findLibrary("androidx-activity-ktx").get())
                    "implementation"(libs.findLibrary("androidx-lifecycle-runtime-ktx").get())

                    "implementation"(libs.findLibrary("androidx-compose-material3").get())
                    "implementation"(libs.findLibrary("androidx-compose-ui-google-fonts").get())
                    "implementation"(libs.findLibrary("androidx-navigation-compose").get())

                    // Coil
                    "implementation"(libs.findLibrary("coil-compose").get())
                    "implementation"(libs.findLibrary("coil-network-okhttp").get())

                    // Android Studio Preview support
                    "debugImplementation"(libs.findLibrary("androidx-compose-ui-tooling").get())
                    "implementation"(libs.findLibrary("androidx-compose-ui-tooling-preview").get())

                    // Immutable Collections (for View States)
                    "implementation"(libs.findLibrary("kotlinx-collections-immutable").get())

                    // Test support
                    "debugImplementation"(
                        libs.findLibrary("androidx-compose-ui-test-manifest").get()
                    )

                    // Good library that adds lint checks to compose modules: https://github.com/slackhq/compose-lints
                    "lintChecks"(libs.findLibrary("compose-lint-checks").get())
                }
            }

            composeCompiler {
                reportsDestination.set(project.layout.buildDirectory.dir("reports/compose-reports"))
                metricsDestination.set(project.layout.buildDirectory.dir("reports/compose-metrics"))
            }

            /**
             * After the project is evaluated, check if Koin is used as a dependency.
             * If Koin is present, adds the "koin-compose-viewmodel-navigation" dependency.
             */
            afterEvaluate {
                val usesKoin =
                    project.configurations
                        .getByName("implementation").allDependencies
                        .any { it.group.orEmpty().contains("io.insert-koin") }

                if (usesKoin) {
                    dependencies {
                        "implementation"(
                            libs.findLibrary("koin-compose-viewmodel-navigation").get()
                        )
                    }
                }
            }
        }
    }
}
import com.diffplug.gradle.spotless.BaseKotlinExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * A convention plugin that applies common configurations for code quality to all modules.
 *
 * It configures Spotless and Detekt plugins.
 *
 * Inspired by:
 * - [Home Assistant repo](https://github.com/home-assistant/android/tree/main/build-logic)
 * - [An interesting Medium article](https://medium.com/@sridhar-sp/simplify-your-android-builds-a-guide-to-convention-plugins-b9fea8c5e117)
 */
class CodeQualityConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            apply(plugin = libs.findPluginId("spotless"))
            apply(plugin = libs.findPluginId("detekt"))

            spotless {
                kotlin {
                    target("**/*.kt")
                    ktlint(libs)
                }

                kotlinGradle {
                    target("**/*.gradle.kts")
                    ktlint(libs)
                }
            }

            detekt {
                parallel = true
                buildUponDefaultConfig = true
            }

            dependencies {
                "detektPlugins"(libs.findLibrary("detekt-compose").get())
            }

            /**
             * This task is used to run all the code quality checks in one go (Spotless, Lint and Detekt).
             *
             * It can be run by executing `./gradlew codeQualityCheck`
             */
            tasks.register("codeQualityCheck") {
                group = "verification"
                description = "Runs Spotless, Lint and Detekt"

                dependsOn("spotlessCheck", "detektDebug", "lintRelease")
            }
        }
    }

    fun BaseKotlinExtension.ktlint(libs: VersionCatalog) {
        ktlint(libs.findVersion("ktlint").get().toString()).editorConfigOverride(
            mapOf(
                "ktlint_standard_function-naming" to "disabled",
                "ktlint_standard_function-expression-body" to "disabled"
            )
        )

        trimTrailingWhitespace()
        endWithNewline()
    }
}
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * A convention plugin that applies the UI configurations needed by our UI modules.
 * This prevents duplication across multiple modules.
 *
 * Under the hood, this plugin applies the Common Config, Compose & Dependencies Convention Plugin.
 *
 * Inspired by:
 * - [Home Assistant repo](https://github.com/home-assistant/android/tree/main/build-logic)
 * - [An interesting Medium article](https://medium.com/@sridhar-sp/simplify-your-android-builds-a-guide-to-convention-plugins-b9fea8c5e117)
 */
class AndroidUiConventionPlugin : Plugin<Project> {

    val pluginsToApply = listOf(
        AndroidConfigConventionPlugin(),
        AndroidComposeConventionPlugin(),
        AndroidDependenciesConventionPlugin(),
    )

    override fun apply(target: Project) {
        with(target) {
            pluginsToApply.forEach { plugin -> plugin.apply(target) }

            androidConfig {
                dependencies {
                    "implementation"(project(":core:viewmodel"))
                    "implementation"(project(":core:ui:component"))
                    "implementation"(project(":core:ui:navigation"))
                    "implementation"(project(":core:ui:theme"))
                }
            }
        }
    }
}
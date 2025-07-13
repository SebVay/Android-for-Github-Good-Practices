import com.diffplug.gradle.spotless.BaseKotlinExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * A convention plugin that applies common configurations
 * to Android modules (Application and Library), preventing duplication across multiple modules.
 *
 * It also applies several Gradle plugins that are commonly used in all android modules.
 *
 * Inspired by:
 * - [Home Assistant repo](https://github.com/home-assistant/android/tree/main/build-logic)
 * - [An interesting Medium article](https://medium.com/@sridhar-sp/simplify-your-android-builds-a-guide-to-convention-plugins-b9fea8c5e117)
 */
class AndroidConfigConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        CodeQualityConventionPlugin().apply(target)

        with(target) {
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            apply(plugin = libs.findPluginId("kotlin-android"))
            apply(plugin = libs.findPluginId("kotlin-serialization"))
            apply(plugin = libs.findPluginId("kotlin-parcelize"))

            androidConfig {
                compileSdk = libs.getVersionInt("androidCompileSdk")

                defaultConfig {
                    minSdk = libs.getVersionInt("androidMinSdk")
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                }

                compileOptions {
                    sourceCompatibility = JavaVersion.toVersion(libs.getVersionInt("javaVersion"))
                    targetCompatibility = JavaVersion.toVersion(libs.getVersionInt("javaVersion"))
                }

                tasks.withType<KotlinCompile>().configureEach {
                    compilerOptions {
                        jvmTarget.set(
                            JvmTarget.fromTarget(libs.getVersionInt("javaVersion").toString())
                        )
                    }
                }

                buildFeatures {
                    buildConfig = true
                }

                lint {
                    abortOnError = true
                    warningsAsErrors = true
                    baseline = file("lint-baseline.xml")
                    lintConfig = file("lint.xml")
                }
            }

            /**
             * This task is used to run all the code quality checks in one go.
             *
             * It depends on Spotless, Lint and Detekt tasks.
             * It can be run by executing `./gradlew codeQualityCheck`
             */
/*
            tasks.register("codeQualityCheck") {
                group = "verification"
                description = "Runs Spotless, Lint and Detekt"

                dependsOn("spotlessCheck", "detekt", "lint")
            }
*/
        }
    }
}

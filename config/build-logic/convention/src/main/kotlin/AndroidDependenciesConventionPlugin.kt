import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType

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
                    val koinBom = platform(libs.findLibrary("koin-bom").get())

                    "androidTestImplementation"(koinBom)
                    "implementation"(koinBom)

                    "implementation"(libs.findLibrary("timber").get())
                    "implementation"(libs.findLibrary("koin").get())

                    // Our custom lint rules are imported from here
                    "lintChecks"(project(":config:lint-rules"))

                    // JUnit 5 (aka. Jupiter)
                    "testImplementation"(platform(libs.findLibrary("junit-bom").get()))
                    "testImplementation"(libs.findLibrary("junit-jupiter-core").get())
                    "testRuntimeOnly"(libs.findLibrary("junit-platform-launcher").get())

                    "testImplementation"(libs.findLibrary("mockk").get())
                    "testImplementation"(libs.findLibrary("kotest-runner-junit5").get())
                    "testImplementation"(libs.findLibrary("kotest-assertions-core").get())
                    "testImplementation"(libs.findLibrary("kotlinx-coroutines-test").get())
                    "testImplementation"(libs.findLibrary("turbine").get())
                    "testImplementation"(libs.findLibrary("koin-test-junit5").get())
                }

                tasks.withType<Test> {
                    useJUnitPlatform()
                }
            }
        }
    }
}
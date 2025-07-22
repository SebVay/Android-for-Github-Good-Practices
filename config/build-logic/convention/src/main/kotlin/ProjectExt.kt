import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.diffplug.gradle.spotless.SpotlessExtension
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

/**
 * Provides the `androidConfig {}` block in a graceful way.
 */
internal fun Project.androidConfig(block: CommonExtension<*, *, *, *, *, *>.() -> Unit) {
    when (extensions.findByName("android")) {

        is LibraryExtension -> extensions.configure<LibraryExtension> {
            block()
        }

        is ApplicationExtension -> extensions.configure<ApplicationExtension> {
            block()
        }
    }
}

/**
 * Provides the `spotless {}` block in a graceful way.
 */
internal fun Project.spotless(block: SpotlessExtension.() -> Unit) {
    extensions.configure<SpotlessExtension> {
        block()
    }
}

/**
 * Provides the `detekt {}` block in a graceful way.
 */
internal fun Project.detekt(block: DetektExtension.() -> Unit) {
    extensions.configure<DetektExtension> {
        block()
    }
}

/**
 * Provides the `composeCompiler {}` block in a graceful way.
 */
internal fun Project.composeCompiler(block: ComposeCompilerGradlePluginExtension.() -> Unit) {
    extensions.configure<ComposeCompilerGradlePluginExtension> {
        block()
    }
}

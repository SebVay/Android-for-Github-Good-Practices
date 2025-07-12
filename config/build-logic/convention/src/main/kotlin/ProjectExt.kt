import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.diffplug.gradle.spotless.SpotlessExtension
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

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
    extensions.configure(SpotlessExtension::class.java) {
        block()
    }
}

/**
 * Provides the `detekt {}` block in a graceful way.
 */
internal fun Project.detekt(block: DetektExtension.() -> Unit) {
    extensions.configure(DetektExtension::class.java) {
        block()
    }
}

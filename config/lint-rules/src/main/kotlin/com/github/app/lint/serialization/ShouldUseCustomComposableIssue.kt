package com.github.app.lint.serialization

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import org.jetbrains.uast.UElement
import org.jetbrains.uast.UFile
import org.jetbrains.uast.UImportStatement
import java.util.EnumSet

object ShouldUseCustomComposableIssue {
    val ISSUE = Issue.create(
        id = "ShouldUseCustomComposableIssue",
        briefDescription = "This class should not use any $MATERIAL3 components.",
        explanation =
            "To ensure consistency, maintainability, and reusability, it's a common practice to use our custom `@Composable` components " +
                "instead of directly using `$MATERIAL3` components. " +
                "This approach allows for centralized styling, easier updates, and better adherence to the application's design system.\n" +
                "The `@Composable` components that should be used instead are located in the `core:ui:component` module or in a `component` package.\n" +
                "The Scaffold `@Composable` is authorized to be used directly in the project.",
        category = Category.CORRECTNESS,
        priority = 6,
        severity = Severity.WARNING,
        implementation = Implementation(
            ShouldUseCustomComposableDetector::class.java,
            EnumSet.of(Scope.JAVA_FILE, Scope.TEST_SOURCES),
            Scope.JAVA_FILE_SCOPE
        )
    )

    internal class ShouldUseCustomComposableDetector : Detector(), Detector.UastScanner {
        override fun createUastHandler(context: JavaContext): UElementHandler {
            return ShouldUseCustomComposableHandler(context)
        }

        override fun getApplicableUastTypes(): List<Class<out UElement>> {
            return listOf(UFile::class.java)
        }
    }

    private class ShouldUseCustomComposableHandler(
        private val context: JavaContext
    ) : UElementHandler() {

        override fun visitFile(node: UFile) {
            if (!isModuleAllowed() && !node.isPackageAllowed()) {
                node.imports.forEach(::visitImport)
            }
        }

        private fun UFile.isPackageAllowed(): Boolean {
            return packageName.contains(".component")
        }

        private fun isModuleAllowed(): Boolean {
            return ALLOWED_MODULES.contains(context.project.name)
        }

        /**
         * Visits an import statement and reports an issue if it directly references `androidx.compose.material3`
         * and is not allowed.
         *
         * @param node The UImportStatement to check.
         */
        private fun visitImport(node: UImportStatement) {
            if (
                node.isMaterial3Import() && node.isAllowed().not()
            ) {
                context.report(
                    issue = ISSUE,
                    location = context.getLocation(node),
                    message = "Do not use direct reference to `androidx.compose.material3`." +
                        "Instead create a `@Composable` App component in `core:ui:component`."
                )
            }
        }

        /**
         * Checks if the import statement is allowed.
         *
         * Currently, only the Scaffold composable from Material 3 is allowed.
         *
         * @return `true` if the import is allowed, `false` otherwise.
         */
        private fun UImportStatement.isAllowed(): Boolean {
            return importReference?.asSourceString() == SCAFFOLD_QUALIFIED_NAME
        }

        /**
         * Checks if the import statement is for a Material 3 component.
         *
         * @return `true` if the import is for a Material 3 component, `false` otherwise.
         */
        private fun UImportStatement.isMaterial3Import(): Boolean {
            return importReference?.asSourceString().orEmpty().startsWith(MATERIAL3)
        }
    }

    private const val THEME_MODULE = "theme"
    private const val COMPONENT_MODULE = "component"
    private val ALLOWED_MODULES = arrayOf(THEME_MODULE, COMPONENT_MODULE)
    private const val MATERIAL3 = "androidx.compose.material3"
    private const val SCAFFOLD_QUALIFIED_NAME = "$MATERIAL3.Scaffold"
}

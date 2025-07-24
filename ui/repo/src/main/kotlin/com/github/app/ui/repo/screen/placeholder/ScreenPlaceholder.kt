package com.github.app.ui.repo.screen.placeholder

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.github.app.core.ui.navigation.screen.Screen

// Placeholder that demonstrate navigation and the predictive back handler feature)
// Will be here as long as we won't have content on second screen
@Stable
object ScreenPlaceholder : Screen("ScreenPlaceholder") {

    @Composable
    override fun Content() {
        // Empty
    }
}

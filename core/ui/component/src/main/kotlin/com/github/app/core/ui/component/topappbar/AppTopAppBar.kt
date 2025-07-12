package com.github.app.core.ui.component.topappbar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.github.app.core.ui.component.text.BATitleLarge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BATopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    containerColor: Color = Color.Unspecified,
) {
    TopAppBar(
        modifier = modifier,
        title = { BATitleLarge(title) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor,
        ),
    )
}

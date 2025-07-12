package com.github.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.github.app.core.ui.theme.GithubAppTheme
import com.github.app.graph.Graph

class GithubAppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GithubAppTheme {
                Graph()
            }
        }
    }
}

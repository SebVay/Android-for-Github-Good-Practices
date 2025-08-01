package com.github.app.ui.ext

import com.github.app.core.viewmodel.AppViewModel
import com.github.app.core.viewmodel.AppViewState


/**
 * A convenience function for testing to access the current view state of an [AppViewModel].
 */
fun <VS : AppViewState> AppViewModel<VS>.viewState(): VS {
    return viewStateFlow.value
}
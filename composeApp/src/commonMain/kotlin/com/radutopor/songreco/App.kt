package com.radutopor.songreco

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.radutopor.songreco.features.root.impl.RootStore
import com.radutopor.songreco.features.root.impl.RootUi
import com.radutopor.songreco.features.root.impl.rootModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.getKoinOrNull

private val appModule = module {
    includes(rootModule)
    viewModel { AppStore() }
}

private fun appInit() {
    getKoinOrNull() ?: startKoin { modules(appModule) }
}

private class AppStore : ViewModel(), KoinComponent {
    // Only coroutine executed on the Main dispatcher is collecting the app state (for UI composition), any other coroutines are executed in
    // the non-blocking thread pool
    private val rootScope = CoroutineScope(viewModelScope.coroutineContext + Dispatchers.IO)
    val state = get<RootStore> { parametersOf(rootScope) }.state
}

@Composable
fun AppUi() {
    remember { appInit() }
    val state by koinViewModel<AppStore>().state.collectAsStateWithLifecycle()
    RootUi(state)
}

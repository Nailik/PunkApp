package com.example.punkapp.view

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.punkapp.backend.data.Beer
import com.example.punkapp.viewmodel.OverviewViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * List of peers with pagination
 */
@Composable
fun OverviewView(viewModel: OverviewViewModel = viewModel()) {
    val data: LazyPagingItems<Beer> = viewModel.beer.collectAsLazyPagingItems()

    var isElementExpanded by rememberSaveable {
        mutableStateOf(false)
    }
    val state = rememberLazyListState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = state,
        userScrollEnabled = !isElementExpanded
    ) {
        items(count = data.itemCount, itemContent = { index ->
            data[index]?.also { beer ->
                ListElement(beer, onExpand = {
                    isElementExpanded = it
                    if (it) {
                        CoroutineScope(Dispatchers.Main).launch {
                            state.scrollToItem(index)
                        }
                    }
                })
            }
        })
    }
}

@Composable
fun BackHandler(enabled: Boolean = true, onBack: () -> Unit) {
    // Safely update the current `onBack` lambda when a new one is provided
    val currentOnBack by rememberUpdatedState(onBack)
    // Remember in Composition a back callback that calls the `onBack` lambda
    val backCallback = remember {
        object : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() {
                currentOnBack()
            }
        }
    }
    // On every successful composition, update the callback with the `enabled` value
    SideEffect {
        backCallback.isEnabled = enabled
    }
    val backDispatcher = checkNotNull(LocalOnBackPressedDispatcherOwner.current) {
        "No OnBackPressedDispatcherOwner was provided via LocalOnBackPressedDispatcherOwner"
    }.onBackPressedDispatcher
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner, backDispatcher) {
        // Add callback to the backDispatcher
        backDispatcher.addCallback(lifecycleOwner, backCallback)
        // When the effect leaves the Composition, remove the callback
        onDispose {
            backCallback.remove()
        }
    }
}

/**
 * one list element displays a card
 * when clicked, navigation to the detail view is started
 */
@Composable
fun LazyItemScope.ListElement(beer: Beer, onExpand: (expanded: Boolean) -> Unit) {

    var isExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    BackHandler(isExpanded) {
        isExpanded = !isExpanded
        onExpand(isExpanded)
    }

    ItemView(beer, isExpanded, onClick = {
        isExpanded = !isExpanded
        onExpand(isExpanded)
    })
}

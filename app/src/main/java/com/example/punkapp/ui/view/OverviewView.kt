package com.example.punkapp.ui.view

import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.punkapp.backend.data.Beer
import com.example.punkapp.ui.icons.Tune
import com.example.punkapp.viewmodel.OverviewViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * List of peers with pagination
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OverviewView(viewModel: OverviewViewModel = viewModel()) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    var isElementExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    val topBarOpacity by animateFloatAsState(
        targetValue = if (isElementExpanded) 0f else 1f,
        animationSpec = tween(200)
    )

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                modifier = Modifier
                    .alpha(topBarOpacity)
                    .let { if (isElementExpanded) it.height(0.dp) else it },
                title = { Text("Punk APi") },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    scrolledContainerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(modifier = Modifier
                .alpha(topBarOpacity)
                .let { if (isElementExpanded) it.height(0.dp) else it }, onClick = {  }) {
                Icon(Tune, "")
            }
        }) { padding ->
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            val data: LazyPagingItems<Beer> = viewModel.beer.collectAsLazyPagingItems()

            val state = rememberLazyListState()

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = state,
                userScrollEnabled = !isElementExpanded
            ) {
                items(count = data.itemCount, itemContent = { index ->
                    data[index]?.also { beer ->
                        ListElement(beer, onExpand = { isExpanded ->
                            isElementExpanded = isExpanded
                            if (isExpanded) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    state.scrollToItem(index)
                                }
                            }
                        })
                    }
                })
            }

        }
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

package com.example.punkapp.ui.view

import android.app.Activity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.punkapp.backend.data.Beer
import com.example.punkapp.ui.icons.Tune
import com.example.punkapp.viewmodel.MainViewModel
import com.example.punkapp.viewmodel.RangeFilterNumber
import com.example.punkapp.viewmodel.RangeFilterText
import com.example.punkapp.viewmodel.SingleFilterText
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * List of peers with pagination
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun OverviewView(viewModel: MainViewModel = viewModel()) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    var isElementExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    val topBarOpacity by animateFloatAsState(
        targetValue = if (isElementExpanded) 0f else 1f,
        animationSpec = tween(200)
    )

    val elevation by animateDpAsState(
        targetValue = if (!isElementExpanded) 0.dp else 12.dp,
        animationSpec = tween(200)
    )

    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    val coroutineScope = rememberCoroutineScope()

    val cornerRadius by animateDpAsState(
        targetValue = if (sheetState.offset.value < 1f) 0.dp else 16.dp, animationSpec = tween(200)
    )

    val view = LocalView.current
    if (!view.isInEditMode) {
        val colorScheme = MaterialTheme.colorScheme
        (view.context as Activity).window.also {
            it.statusBarColor = colorScheme.surfaceColorAtElevation(elevation).toArgb()
            it.navigationBarColor = colorScheme.surfaceColorAtElevation(elevation).toArgb()
        }

    }

    ModalBottomSheetLayout(
        sheetShape = MaterialTheme.shapes.large.copy(
            topStart = CornerSize(cornerRadius),
            topEnd = CornerSize(cornerRadius),
            bottomStart = CornerSize(0),
            bottomEnd = CornerSize(0)
        ),
        sheetElevation = 16.dp,
        sheetBackgroundColor = MaterialTheme.colorScheme.surface,
        sheetContentColor = contentColorFor(MaterialTheme.colorScheme.surface),
        sheetState = sheetState,
        sheetContent = {
            FilterBottomSheet(viewModel)
        }
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                SmallTopAppBar(
                    modifier = Modifier
                        .alpha(topBarOpacity)
                        .let { if (isElementExpanded) it.height(0.dp) else it },
                    title = { Text("Punk App") },
                    scrollBehavior = scrollBehavior,
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        scrolledContainerColor = MaterialTheme.colorScheme.background
                    )
                )
            },
            floatingActionButton = {
                FloatingActionButton(modifier = Modifier
                    .alpha(topBarOpacity)
                    .let { if (isElementExpanded) it.height(0.dp) else it },
                    onClick = { coroutineScope.launch { sheetState.show() } }) {
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
                LaunchedEffect(true) {
                    viewModel.refreshData.collect { value ->
                        if (value) {
                            viewModel.refreshData.value = false
                            data.refresh()
                        }
                    }
                }

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
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(viewModel: MainViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        //Drag Handle
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 8.dp)
                .width(32.dp)
                .height(4.dp)
                .clip(MaterialTheme.shapes.extraSmall)
                .background(
                    MaterialTheme
                        .colorScheme
                        .onSurfaceVariant.copy(alpha = 0.5f)
                )
        )


        val filters by viewModel.filtersUIData.collectAsState()

        FlowRow(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .align(Alignment.CenterHorizontally),
            mainAxisSpacing = 8.dp
        ) {
            filters.forEach { filter ->
                FilterChip(
                    label = {
                        Text(filter.name)
                    },
                    selected = filter.isEnabled,
                    onClick = { viewModel.toggleFilter(filter) },
                )
            }
        }

        val state = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(state)
        ) {
            filters.filter { it.isEnabled }.forEach { filter ->
                when (filter) {
                    is RangeFilterText -> RangeFilterTextView(filter, viewModel)
                    is RangeFilterNumber -> RangeFilterNumberView(filter, viewModel)
                    is SingleFilterText -> SingleFilterTextView(filter, viewModel)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RangeFilterTextView(filter: RangeFilterText, viewModel: MainViewModel) {
    ListItem(
        headlineText = {
            Row {
                FilterTextField(
                    modifier = Modifier.weight(1f),
                    label = "${filter.name} Min",
                    value = filter.valueStart ?: "",
                    onValueChange = { viewModel.setFilterData(filter, it, filter.valueEnd) },
                    onClear = { viewModel.setFilterData(filter, null, filter.valueEnd) })
                Spacer(modifier = Modifier.width(5.dp))
                FilterTextField(
                    modifier = Modifier.weight(1f),
                    label = "${filter.name} Max",
                    value = filter.valueEnd ?: "",
                    onValueChange = { viewModel.setFilterData(filter, filter.valueStart, it) },
                    onClear = { viewModel.setFilterData(filter, filter.valueEnd, null) })
            }
        },
        trailingContent = {
            Icon(Icons.Filled.Delete, "", modifier = Modifier
                .clip(CircleShape)
                .clickable {
                    viewModel.removeFilter(filter)
                })
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RangeFilterNumberView(filter: RangeFilterNumber, viewModel: MainViewModel) {
    ListItem(
        headlineText = {
            Row {
                FilterTextField(
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    label = "${filter.name} Min",
                    value = filter.valueStart?.toString(),
                    onValueChange = { viewModel.setFilterData(filter, it, filter.valueEnd.toString()) },
                    onClear = { viewModel.setFilterData(filter, null, filter.valueEnd.toString()) })
                Spacer(modifier = Modifier.width(5.dp))
                FilterTextField(
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    label = "${filter.name} Max",
                    value = filter.valueEnd?.toString(),
                    onValueChange = { viewModel.setFilterData(filter, filter.valueStart.toString(), it) },
                    onClear = { viewModel.setFilterData(filter, filter.valueEnd.toString(), null) })
            }
        }, trailingContent = {
            Icon(Icons.Filled.Delete, "", modifier = Modifier
                .clip(CircleShape)
                .clickable {
                    viewModel.removeFilter(filter)
                }
                .padding(8.dp))
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleFilterTextView(filter: SingleFilterText, viewModel: MainViewModel) {
    ListItem(
        headlineText = {
            FilterTextField(
                modifier = Modifier.fillMaxWidth(),
                label = filter.name,
                value = filter.value,
                onValueChange = { viewModel.setFilterData(filter, it) },
                onClear = { viewModel.setFilterData(filter, null) })
        }, trailingContent = {
            Icon(Icons.Filled.Delete, "", modifier = Modifier
                .clip(CircleShape)
                .clickable {
                    viewModel.removeFilter(filter)
                }
                .padding(8.dp))
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterTextField(
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    label: String,
    value: String?,
    onValueChange: (String) -> Unit,
    onClear: () -> Unit
) {
    TextField(
        modifier = modifier,
        value = value ?: "",
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        trailingIcon = {
            if (value.toString().isNotEmpty()) {
                Icon(
                    Icons.Filled.Clear, "", modifier = Modifier
                        .clip(CircleShape)
                        .clickable(onClick = onClear)
                        .padding(8.dp)
                )
            }
        },
        label = { Text(label) })
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

package com.example.punkapp.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
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

/**
 * one list element displays a card
 * when clicked, navigation to the detail view is started
 */
@Composable
fun LazyItemScope.ListElement(beer: Beer, onExpand: (expanded: Boolean) -> Unit) {
    var isExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    ItemView(beer, isExpanded, onClick = {
        isExpanded = !isExpanded
        onExpand(isExpanded)
    })
}

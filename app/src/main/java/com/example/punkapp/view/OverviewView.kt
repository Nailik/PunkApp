package com.example.punkapp.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.punkapp.MainActivity.Companion.LocalNavController
import com.example.punkapp.backend.data.Beer
import com.example.punkapp.viewmodel.OverviewViewModel

@Composable
fun OverviewView(viewModel: OverviewViewModel = viewModel()) {
    val data: LazyPagingItems<Beer> = viewModel.beer.collectAsLazyPagingItems()
    val navController = LocalNavController.current

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(count = data.itemCount, itemContent = { index ->

            Text(data[index]?.name ?: "", modifier = Modifier
                .padding(16.dp)
                .clickable {
                    data[index]?.also {
                        navController.navigate("detailView/${it.id}")
                    }
                })

        })
    }

}
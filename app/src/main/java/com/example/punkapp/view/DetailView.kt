package com.example.punkapp.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.punkapp.viewmodel.DetailViewModel

@Composable
fun DetailView(id: Int, viewModel: DetailViewModel = viewModel()) {

    val beer = viewModel.getBeer(id).collectAsState(initial = null).value

    Text(beer?.name ?: "", modifier = Modifier
        .fillMaxSize())

}
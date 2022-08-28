package com.example.punkapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.punkapp.backend.data.Beer
import kotlinx.coroutines.flow.Flow

class OverviewViewModel : ViewModel() {

    val beer: Flow<PagingData<Beer>> = Pager(PagingConfig(pageSize = 25)) {
        BeerSource()
    }.flow.cachedIn(viewModelScope)


}
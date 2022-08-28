package com.example.punkapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.punkapp.backend.PunkApiClient
import com.example.punkapp.backend.data.Beer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DetailViewModel : ViewModel() {

    fun getBeer(id: Int): Flow<Beer> = flow {
        emit(PunkApiClient.getBeerDetail(id))
    }

}
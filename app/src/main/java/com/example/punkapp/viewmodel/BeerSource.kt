package com.example.punkapp.viewmodel

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.punkapp.backend.Parameter
import com.example.punkapp.backend.ParameterType
import com.example.punkapp.backend.PunkApiClient
import com.example.punkapp.backend.data.Beer

class BeerSource : PagingSource<Int, Beer>() {

    override fun getRefreshKey(state: PagingState<Int, Beer>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Beer> {
        val nextPage = params.key ?: 1
        println("load $nextPage")
        val parameters = listOf(Parameter(ParameterType.PageSize, 25.toString()), Parameter(ParameterType.PageNumber, nextPage.toString()))
        val data = PunkApiClient.getBeerList(parameters)

        return LoadResult.Page(
            data = data,
            prevKey = if (nextPage == 1) null else nextPage - 1,
            nextKey = nextPage + 1
        )

    }
}
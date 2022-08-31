package com.example.punkapp.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.punkapp.backend.Parameter
import com.example.punkapp.backend.data.Beer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    var parameterList = mutableListOf<Parameter>()
    private var filterList = mutableStateListOf<Filter>().apply {
        addAll(Filter.filterList)
    }
    private val filters: MutableStateFlow<List<Filter>> = MutableStateFlow(filterList)
    val filtersUIData = filters.asStateFlow()

    val beer: Flow<PagingData<Beer>> = Pager(PagingConfig(pageSize = 25)) {
        BeerSource(parameterList)
    }.flow.cachedIn(viewModelScope)

    var refreshData = MutableStateFlow(false)

    private fun updateParameterList() {
        parameterList.clear()
        filterList.filter { it.isEnabled }.forEach { filter ->

            when (filter) {
                is RangeFilterText -> {
                    if (!filter.valueStart.isNullOrEmpty()) {
                        parameterList.add(Parameter(filter.parameterTypeStart, filter.valueStart.toString().replace(" ", "_")))
                    }
                    if (!filter.valueEnd.isNullOrEmpty()) {
                        parameterList.add(Parameter(filter.parameterTypeEnd, filter.valueEnd.toString().replace(" ", "_")))
                    }
                }
                is RangeFilterNumber -> {
                    if (!filter.valueStart?.toString().isNullOrEmpty()) {
                        parameterList.add(Parameter(filter.parameterTypeStart, filter.valueStart.toString()))
                    }
                    if (!filter.valueEnd?.toString().isNullOrEmpty()) {
                        parameterList.add(Parameter(filter.parameterTypeEnd, filter.valueEnd.toString()))
                    }
                }
                is SingleFilterText -> {
                    if (!filter.value.isNullOrEmpty()) {
                        parameterList.add(Parameter(filter.parameterType, filter.value.toString().replace(" ", "_")))
                    }
                }
            }
        }
        refreshData.value = true
    }

    fun toggleFilter(filter: Filter) {

        val index = filterList.indexOf(filter)
        filterList = filterList.also { list ->
            list[index] = when (filter) {
                is RangeFilterText -> filter.copy(isEnabled = !filter.isEnabled)
                is RangeFilterNumber -> filter.copy(isEnabled = !filter.isEnabled)
                is SingleFilterText -> filter.copy(isEnabled = !filter.isEnabled)
                else -> filter
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            filters.value = filterList
        }
    }

    fun removeFilter(filter: Filter) {
        val index = filterList.indexOf(filter)
        filterList = filterList.also { list ->
            list[index] = filter.also { it.isEnabled = false }
        }

        viewModelScope.launch(Dispatchers.IO) {
            filters.value = filterList
            updateParameterList()
        }
    }


    fun setFilterData(filter: SingleFilterText, data: String?) {
        val index = filterList.indexOf(filter)
        filterList = filterList.also {
            it[index] = filter.copy(value = data)
        }

        viewModelScope.launch(Dispatchers.IO) {
            filters.emit(filterList)
            updateParameterList()
        }
    }

    fun setFilterData(filter: RangeFilterText, dataStart: String?, dataEnd: String?) {
        val index = filterList.indexOf(filter)
        filterList = filterList.also {
            it[index] = filter.copy(valueStart = dataStart, valueEnd = dataEnd)
        }

        viewModelScope.launch(Dispatchers.IO) {
            filters.emit(filterList)
            updateParameterList()
        }
    }

    fun setFilterData(filter: RangeFilterNumber, dataStart: String?, dataEnd: String?) {
        val valueStart = dataStart?.let {
            //on invalid number use previous value
            it.toDoubleOrNull() ?: filter.valueStart
        }

        val valueEnd = dataEnd?.let {
            //on invalid number use previous value
            it.toDoubleOrNull() ?: filter.valueEnd
        }

        val index = filterList.indexOf(filter)
        filterList = filterList.also {
            it[index] = filter.copy(valueStart = valueStart, valueEnd = valueEnd)
        }

        viewModelScope.launch(Dispatchers.IO) {
            filters.emit(filterList)
            updateParameterList()
        }
    }

}
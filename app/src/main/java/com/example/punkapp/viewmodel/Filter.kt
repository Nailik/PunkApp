package com.example.punkapp.viewmodel

import com.example.punkapp.backend.ParameterType


open class Filter(open val name: String, open var isEnabled: Boolean) {
    companion object {
        private val ABV = RangeFilterNumber("ABV", ParameterType.ABVGreater, ParameterType.ABVLess, null, null)
        private val IBU = RangeFilterNumber("IBU", ParameterType.IBUGreater, ParameterType.IBULess, null, null)
        private val EBC = RangeFilterNumber("EBC", ParameterType.EBCGreater, ParameterType.EBCLess, null, null)
        private val Date = RangeFilterText("Date", ParameterType.BrewedAfter, ParameterType.BrewedBefore, null, null)
        private val Name = SingleFilterText("Name", ParameterType.BeerName, null)
        private val Yeast = SingleFilterText("Yeast", ParameterType.Yeast, null)
        private val Hops = SingleFilterText("Hops", ParameterType.Hops, null)
        private val Malt = SingleFilterText("Malt", ParameterType.Malt, null)
        private val Food = SingleFilterText("Food", ParameterType.Food, null)

        val filterList: List<Filter>
            get() = listOf(Name, Food, Hops, Malt, Yeast, ABV, IBU, EBC, Date)
    }

}

data class RangeFilterText(
    override val name: String,
    val parameterTypeStart: ParameterType, val parameterTypeEnd: ParameterType,
    var valueStart: String?, var valueEnd: String?,
    override var isEnabled: Boolean = false
) : Filter(name, isEnabled)

data class RangeFilterNumber(
    override val name: String,
    val parameterTypeStart: ParameterType, val parameterTypeEnd: ParameterType,
    var valueStart: Double?, var valueEnd: Double?,
    override var isEnabled: Boolean = false
) : Filter(name, isEnabled)

data class SingleFilterText(
    override val name: String,
    val parameterType: ParameterType,
    var value: String?,
    override var isEnabled: Boolean = false
) : Filter(name, isEnabled)
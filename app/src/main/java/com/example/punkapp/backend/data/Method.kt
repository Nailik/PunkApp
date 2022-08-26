package com.example.punkapp.backend.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Method(
    @SerialName("mash_temp") val mashTemp: List<MashTemp?>?,
    val fermentation: Fermentation?,
    val twist: String?
)

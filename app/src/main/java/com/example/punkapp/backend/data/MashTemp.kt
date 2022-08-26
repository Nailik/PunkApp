package com.example.punkapp.backend.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class MashTemp(
    @SerialName("temp") val temperature: Temperature?,
    val duration: Int?
)
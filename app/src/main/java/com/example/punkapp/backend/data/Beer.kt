package com.example.punkapp.backend.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Beer(
    val id: Int,
    val name: String,
    val tagline: String,
    @SerialName("lang") val firstBrewed: String,
    val description: String,
    @SerialName("image_url") val imageUrl: String,
    val abv: Double,
    val ibu: Double,
    @SerialName("target_fg") val targetFg: Double,
    @SerialName("target_og") val targetOg: Double,
    val ebc: Double,
    val srm: Double,
    val ph: Double,
    @SerialName("attenuation_level") val attenuationLevel: Double,
    val volume: Volume,
    @SerialName("boil_volume") val boilVolume: Volume,
    val method: Method,
    val fermentation: Fermentation,
    val ingredients: Ingredients,
    @SerialName("food_pairing") val foodPairing: List<String>,
    @SerialName("brewers_tips") val brewersTips: String,
    @SerialName("contributed_by") val contributedBy: String
)
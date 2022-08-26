package com.example.punkapp.backend.data

import kotlinx.serialization.Serializable

@Serializable
data class Ingredients(val malt: List<Malt>, val hops: List<Hops>, val yeast: String)
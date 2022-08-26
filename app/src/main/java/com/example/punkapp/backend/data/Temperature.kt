package com.example.punkapp.backend.data

import kotlinx.serialization.Serializable

@Serializable
data class Temperature(val value: Int?, val unit: String?)
package com.example.punkapp.backend.data

import kotlinx.serialization.Serializable

@Serializable
data class Volume(val volume: Int, val unit: String)
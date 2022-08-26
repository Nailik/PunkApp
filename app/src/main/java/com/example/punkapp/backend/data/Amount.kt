package com.example.punkapp.backend.data

import kotlinx.serialization.Serializable

@Serializable
data class Amount(val value: Double?, val unit: String?)
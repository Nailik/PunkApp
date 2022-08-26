package com.example.punkapp.backend.data

import kotlinx.serialization.Serializable

@Serializable
data class Hops(val name: String?, val amount: Amount?, val add: String?, val attribute: String?)
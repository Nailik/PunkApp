package com.example.punkapp.backend

import com.example.punkapp.backend.data.Beer
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object PunkApiClient : KoinComponent {

    private const val url = "https://api.punkapi.com/v2/beers"

    private val engine by inject<HttpClientEngine>()

    private val client = HttpClient(engine) {
        install(Logging)
        install(ContentNegotiation) {
            json()
        }
    }

    //curl https://api.punkapi.com/v2/beers
    suspend fun getBeerList(parameter: List<Parameter>? = null): List<Beer> =
        client.get(url) {
            url {
                parameter?.forEach {
                    parameters.append(it.type.name, it.value)
                }
            }
        }.body()

    //curl https://api.punkapi.com/v2/beers/1
    suspend fun getBeerDetail(id: Int): Beer =
        client.get("$url/$id").body<List<Beer>>().first()

    //curl https://api.punkapi.com/v2/beers/random
    suspend fun getBeerRandom(): Beer =
        client.get("$url/random").body<List<Beer>>().first()
}
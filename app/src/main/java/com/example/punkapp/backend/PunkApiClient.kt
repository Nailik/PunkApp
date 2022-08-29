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

/**
 * Implementation of PunkApi
 * HttpClientEngine is injected by Koin to be exchanged for testing
 */
object PunkApiClient : KoinComponent {

    private const val url = "https://api.punkapi.com/v2/beers"

    private val engine by inject<HttpClientEngine>()

    private val client = HttpClient(engine) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json()
        }
    }

    /**
     * returns list of Beers, filtered by Parameters
     */
    //curl https://api.punkapi.com/v2/beers
    suspend fun getBeerList(parameter: List<Parameter>? = null): List<Beer> =
        try {
            client.get(url) {
                url {
                    parameter?.forEach {
                        parameters.append(it.type.value, it.value)
                    }
                }
            }.body()
        }catch (e: Exception){
            emptyList()
        }

    /**
     * returns a single Beer with the given id
     */
    //curl https://api.punkapi.com/v2/beers/1
    suspend fun getBeerDetail(id: Int): Beer =
        client.get("$url/$id").body<List<Beer>>().first()

    /**
     * returns a random Beer from api
     */
    //curl https://api.punkapi.com/v2/beers/random
    suspend fun getBeerRandom(): Beer =
        client.get("$url/random").body<List<Beer>>().first()
}
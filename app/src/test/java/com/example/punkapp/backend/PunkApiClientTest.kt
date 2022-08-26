package com.example.punkapp.backend

import com.example.punkapp.backend.data.Beer
import io.ktor.client.engine.*
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import java.io.File
import kotlin.test.assertEquals

internal class PunkApiClientTest : KoinTest {

    companion object {
        val beerListFile = File("src\\test\\java\\com\\example\\punkapp\\backend\\data\\beerList.json")
        val beerDetailFile = File("src\\test\\java\\com\\example\\punkapp\\backend\\data\\beerDetail.json")
        val beerRandomFile = File("src\\test\\java\\com\\example\\punkapp\\backend\\data\\beerRandom.json")

        @Suppress("UNUSED")
        @BeforeAll
        @JvmStatic
        fun initialize() {
            startKoin {
                modules(
                    module {
                        @Suppress("USeLESS_CAST")
                        single { generateEngineWithResult() as HttpClientEngine }
                    })
            }
        }

        private fun generateEngineWithResult() = MockEngine { request ->
            val content = when (request.url.toString()) {
                "https://api.punkapi.com/v2/beers" -> beerListFile.readText()
                "https://api.punkapi.com/v2/beers/1" -> beerDetailFile.readText()
                "https://api.punkapi.com/v2/beers/random" -> beerRandomFile.readText()
                else -> throw java.lang.RuntimeException("Invalid request")
            }

            respond(
                content = content,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
    }

    @Test
    fun getBeerList() {
        runBlocking {
            val result = PunkApiClient.getBeerList()

            assertEquals(result, Json.decodeFromString(beerListFile.readText()))
        }
    }

    @Test
    fun getBeerDetail() {
        runBlocking {
            val result = PunkApiClient.getBeerDetail(1)

            assertEquals(result, Json.decodeFromString<List<Beer>>(beerDetailFile.readText()).first())
        }
    }

    @Test
    fun getBeerRandom() {
        runBlocking {
            val result = PunkApiClient.getBeerRandom()

            assertEquals(result, Json.decodeFromString<List<Beer>>(beerRandomFile.readText()).first())
        }
    }
}
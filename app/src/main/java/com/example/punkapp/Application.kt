package com.example.punkapp

import io.ktor.client.engine.*
import io.ktor.client.engine.cio.*
import org.koin.core.context.startKoin
import org.koin.dsl.module

class Application : android.app.Application() {

    init {
        startKoin {
            modules(
                module {
                    @Suppress("USeLESS_CAST")
                    single { CIO.create() as HttpClientEngine }
                })
        }
    }

}
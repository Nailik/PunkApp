package com.example.punkapp.backend

import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test

internal class BeerColorCodesTest {

    @Test
    fun getSrmColorCode() {
        val result = Triple(3, 4, 3)

        assertEquals(BeerColorCodes.getSrmColorCode(40.0), result)
        assertEquals(BeerColorCodes.getSrmColorCode(100.0), result)
    }
}
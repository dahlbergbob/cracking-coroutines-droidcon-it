package com.sample.crackingcoroutines.helpers

import kotlinx.coroutines.delay
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.math.max
import kotlin.math.min

object Faker {

    suspend fun calculate(delay: Long): String {
        delay(max(min(5000, delay), 500))
        return "Fake waited ${delay}ms"
    }

    suspend fun forceSuspension() {
        delay(1)
    }
}
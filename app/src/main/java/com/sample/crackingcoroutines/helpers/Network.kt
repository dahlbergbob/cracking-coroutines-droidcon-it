package com.sample.crackingcoroutines.helpers

import androidx.annotation.WorkerThread
import kotlinx.coroutines.delay
import kotlin.concurrent.thread
import kotlin.random.Random
import kotlin.random.nextLong

object Network {

    @WorkerThread
    fun networkFetch(): String {
        val delay = Random.nextLong(200L, 2000L)
        Thread.sleep(delay)
        return "Spent $delay ms in ${Thread.currentThread().name}"
    }

    suspend fun suspendingNetworkFetch(): String {
        val delay = Random.nextLong(200, 2000)
        delay(delay)
        return "String got returned after ${delay}ms"
    }

    fun callback(onSuccess: (String)->Unit) {
        thread {
            onSuccess(networkFetch())
        }
    }
}
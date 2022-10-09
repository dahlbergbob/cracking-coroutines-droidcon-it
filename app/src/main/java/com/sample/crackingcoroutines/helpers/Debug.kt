package com.sample.crackingcoroutines.helpers

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

fun CoroutineScope.info(): String {
    return "Context: $coroutineContext"
}

val threadName: String
    get() = Thread.currentThread().name

fun Job.state(): String {
    return when {
        !isActive && !isCancelled && !isCompleted -> "new"
        isActive && !isCancelled && !isCompleted -> "active/completing"
        !isActive && isCancelled && !isCompleted -> "cancelling"
        !isActive && isCancelled && isCompleted -> "cancelled"
        !isActive && !isCancelled && isCompleted -> "completed"
        else -> "unknown"
    }
}

val Any.javaClassShort: String
    get() = this.javaClass.toString().substringAfterLast(".")
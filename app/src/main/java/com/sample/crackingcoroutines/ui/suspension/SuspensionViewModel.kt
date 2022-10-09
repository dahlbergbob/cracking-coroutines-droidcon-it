package com.sample.crackingcoroutines.ui.suspension

import androidx.lifecycle.viewModelScope
import com.sample.crackingcoroutines.helpers.BaseViewModel
import com.sample.crackingcoroutines.helpers.Network
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.concurrent.thread
import kotlin.coroutines.suspendCoroutine

class SuspensionViewModel : BaseViewModel() {

    fun pauseAndResume() {
        clear("Suspending is")
        viewModelScope.launch {
            outl("PAUSING")
            delay(750)
            outl("and possibly")
            delay(750)
            outl("RESUMING")
            delay(750)
            outl("coroutines")
        }
    }






    fun order() {
        clear("Order")
        out(0)
        viewModelScope.launch {
            out(1)
            launch {
                out(2)
                delay(100)
                out(3)
            }
            launch {
                out(4)
                delay(100)
                out(5)
            }
            out(6)
        }
        out(7)
    }
    // Dispatchers - Maybe revisit later.
    // Change the order by changing dispatchers from Main.immediate to Main













    fun simpleContinuation() {
        clear()
        viewModelScope.launch {
            outp(1)
            suspendCancellableCoroutine {
                outp(2)
                it.resumeWith(Result.success(2))
            }
            outp(3)
        }
    }


    // Which order? Move the out(2)
    // Add delay
    // Add thread






    fun suspendingCallbacks() {
        clear()
        viewModelScope.launch {
            out("Starting")
            val result = suspendingCallSre()
            outl("Got result: $result")
        }
    }
    private suspend fun suspendingCallSre(): String = suspendCancellableCoroutine {
        Network.callback { result ->
            it.resumeWith(Result.success(result))
        }
    }
}
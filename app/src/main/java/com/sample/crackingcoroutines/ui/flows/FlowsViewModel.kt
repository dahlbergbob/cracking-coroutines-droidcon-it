package com.sample.crackingcoroutines.ui.flows

import androidx.lifecycle.viewModelScope
import com.sample.crackingcoroutines.helpers.BaseViewModel
import com.sample.crackingcoroutines.helpers.Network
import com.sample.crackingcoroutines.helpers.javaClassShort
import com.sample.crackingcoroutines.helpers.threadName
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.coroutines.suspendCoroutine

class FlowsViewModel : BaseViewModel() {

    private val flowOfFive = flow {
        repeat(5) {
            emit(threadName)
            delay(250)
        }
    }

    fun flowsOn() {
        clear("Flows on")
        viewModelScope.launch {
            flowOfFive
                .collect {
                    outp("A Got $it")
                }
        }
    }
    // withContext (any change of dispatcher)
    // flowOn
    // double flowOn - with map




    fun launchingIn() {
        clear("Launching in")
        flowOfFive
            .onEach { outp("A Got $it") }
            .launchIn(viewModelScope+Dispatchers.IO)
    }
    // Needs onEach to handle the value
    // How do we switch dispatcher? + (remove flowOn)







    private val exceptionalFlow = flow {
        repeat(2) {
            emit(threadName)
            delay(250)
        }
        throwingMethod()
        emit(threadName)
    }
    private fun throwingMethod() {
        throw Error("From flow ($threadName)")
    }
    fun catching() {
        clear("Catching")
        exceptionalFlow
            .onEach { outp(it) }
            .launchIn(viewModelScope)
    }

    // Add handler +
    // Add catch .catch { outp("catching -> $it") }
    // Throw in transformer










    fun cancelFlow() {
        clear("Cancel")
        flowOf(1,2,3,4,5)
            .onEach { delay(250) }
            .onEach {
                outp("Got: $it")
                if(it == 3) {
                    // How do we cancel the flow?
                }
            }
            .launchIn(viewModelScope)
    }










    private val handler = CoroutineExceptionHandler { _, t ->
        outp("Caught exception: $t")
    }
}
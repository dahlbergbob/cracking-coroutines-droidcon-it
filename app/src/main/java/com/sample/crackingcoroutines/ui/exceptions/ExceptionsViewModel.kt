package com.sample.crackingcoroutines.ui.exceptions

import androidx.lifecycle.viewModelScope
import com.sample.crackingcoroutines.helpers.BaseViewModel
import com.sample.crackingcoroutines.helpers.Network
import com.sample.crackingcoroutines.helpers.javaClassShort
import kotlinx.coroutines.*

class ExceptionsViewModel : BaseViewModel() {

    private val handler = CoroutineExceptionHandler { ctx, exception ->
        outp("Handler got ${exception.javaClassShort}")
    }
    private val customScope = CoroutineScope(
        Dispatchers.Default + handler
    )



    fun exceptionPropagationHierarchy() {
        clear("Exceptions")
        val job = viewModelScope.launch {
            try {
                val job1 = launch { delay(600) }
                val job2 = launch {
                    val job2a = launch {
                        delay(300)
                        throw Error("Quits")
                    }
                    val job2b = launch { delay(800) }
                    job2a.invokeOnCompletion { outp("2a is ${it?.javaClassShort}") }
                    job2b.invokeOnCompletion { outp("2b is ${it?.javaClassShort}") }
                }
                job1.invokeOnCompletion { outp("1 is ${it?.javaClassShort}") }
                job2.invokeOnCompletion { outp("2 is ${it?.javaClassShort}") }
            }
            catch (e:Throwable) {
                outp("Handling exception: ${e.javaClassShort}")
            }
        }
    }
    // Uncaught exceptions are still uncaught exceptions
    // Add handler on scope / not children








    fun supervisorJob() {
        clear("Exceptions supervisor")
        val job = viewModelScope.launch(handler) {
            val job1 = launch { delay(600) }
            val job2 = launch {
                val job2a = launch {
                    delay(300)
                    throw Error("Quits")
                }
                val job2b = launch { delay(800) }
                job2a.invokeOnCompletion { outp("2a is ${it?.javaClassShort}") }
                job2b.invokeOnCompletion { outp("2b is ${it?.javaClassShort}") }
            }
            job1.invokeOnCompletion { outp("1 is ${it?.javaClassShort}") }
            job2.invokeOnCompletion { outp("2 is ${it?.javaClassShort}") }
        }
    }
    // We can add SupervisorJob() but then we break the structure
    // Add supervisorScope







    fun tidyingUpSuspending() {
        clear("Tidying up suspending")
        val job = customScope.launch {
            launch {
                try {
                    launch {
                        delay(800)
                        outp("throwing")
                        throw Error("An error")
                    }.join()
                }
                catch (e:Throwable) {
                    outp("needs tidying up suspending..")
                    val result = Network.suspendingNetworkFetch()
                    outp("tidied up: $result")
                }
            }
        }
    }
    // why doesn't suspending work to tidy up?
    // withContext(NonCancellable)





    fun asyncExceptions() {
        clear("Async errors to handlers")
        val job = viewModelScope.async {
            launch {
                try {
                    launch {
                        delay(800)
                        outp("throwing")
                        throw Error("An error")
                    }.join()
                }
                catch (e:Throwable) {
                    outp("catching")
                }
            }
        }
    }
    // change back to launch
    // called on await
    /*
    CoroutineScope(handler).launch {
        delay(2000)
        job.await()
    }
    */
}
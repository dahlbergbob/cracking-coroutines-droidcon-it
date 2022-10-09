package com.sample.crackingcoroutines.ui.jobs

import androidx.lifecycle.viewModelScope
import com.sample.crackingcoroutines.helpers.BaseViewModel
import com.sample.crackingcoroutines.helpers.state
import kotlinx.coroutines.*

class JobsViewModel : BaseViewModel() {

    private val customScope = CoroutineScope(Dispatchers.Main.immediate)

    fun jobsLifeCycle() {
        val job = Job()
        clear("initial state: "+ job.state())
        customScope.launch(job) {
            launch(Dispatchers.IO) {
                outp("child started: ${job.state()}")
                delay(1000)
            }
        }
        customScope.launch {
            delay(800)
            job.cancel()
            outp("called cancel: ${job.state()}")
        }
        job.invokeOnCompletion {
            outp("On completed: ${job.state()}")
        }
    }






    fun outerJob() {
        clear("Outer job")
        val outerJob = viewModelScope.coroutineContext.job
        viewModelScope.launch {
            val thisJob = coroutineContext.job
            val same = thisJob == outerJob
            val sameAsChild = thisJob == outerJob.children.first()
            outp("Same as outerJob? $same")
            outp("Same as outerJobs first child? $sameAsChild")
        }
    }






    fun joining() {
        clear("Joining job")
        viewModelScope.launch {
            val job = launch {
                outp("inside 1")
                delay(500)
                outp("inside 2")
            }
            job.join()
            outp("outside 1")
        }
    }
    // remove join
    // for async you can also await











    // Go to presentation on Cancellation before next


    fun cancelling() {
        clear("Cancelling job")
        viewModelScope.launch {
            val job = launch {
                outp("inside 1")
                delay(500)
                outp("should never be called")
            }
            delay(250)
            job.cancel()
            outp("outside 1")
        }
    }








    fun cancellingPropagation() {
        clear("Cancelling job")
        val job = viewModelScope.launch {
            val job1 = launch { delay(600) }
            val job2 = launch {
                delay(800)
                cancel("Done")
            }
            val job3 = launch { delay(1000) }
            job1.invokeOnCompletion { outp("1 cancelled ${it is CancellationException}") }
            job2.invokeOnCompletion { outp("2 cancelled ${it is CancellationException}") }
            job3.invokeOnCompletion { outp("3 cancelled ${it is CancellationException}") }

            delay(700)
        }
    }
    // Move cancel outside - only children cancelled













    fun cancellingHandling() {
        clear("Tidying up job")
        viewModelScope.launch {
            val job = launch {
                try {
                    outp("running")
                    delay(800)
                    //cancelFetcher()
                    outp("should not been called")
                }
                catch (e:CancellationException) {
                    outp("Tidying up")
                    throw(e)
                }
            }
            delay(350)
            job.cancelAndJoin()
            outp("Done")
        }
    }
    // We want to rethrow cancellation exceptions
    // show flaky without rethrow

    private suspend fun cancelFetcher() {
        try {
            delay(10_000)
        } catch (t: CancellationException) {
            outp("Fetching an error")
            //throw t
        }
    }













    fun cancellingNonSuspending() {
        clear("Collaborate cancel")
        viewModelScope.launch {
            val job = launch(Dispatchers.Default) {
                outp("inside")
                repeat(5) {
                    Thread.sleep(200)
                    outp("run $it")
                }
                outp("Still inside")
            }
            delay(400)
            outp("call cancel")
            job.cancelAndJoin()
            outp("Done")
        }
    }
    // add yield











    fun cancellingScope() {
        clear("Cancel the scope")
        val job = viewModelScope.launch {
            launch { delay(10_000) }
            launch { delay(14_000) }
        }
        job.invokeOnCompletion {
            outp("Job completed -> cancelled ? ${it is CancellationException}")
        }
        viewModelScope.cancel()
        viewModelScope.launch {
            outp("Starting on a new shiny coroutine")
        }
    }
    // rule reminder
    // cancel children
}
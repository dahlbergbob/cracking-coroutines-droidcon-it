package com.sample.crackingcoroutines.ui.structure

import androidx.lifecycle.viewModelScope
import com.sample.crackingcoroutines.helpers.BaseViewModel
import com.sample.crackingcoroutines.helpers.threadName
import kotlinx.coroutines.*

class StructureViewModel : BaseViewModel() {

    private val customScope = CoroutineScope(Dispatchers.Main.immediate)

    fun awaitingChildren() {
        clear("Awaiting children: ")
        customScope.launch {
            launch {
                delay(2100)
                outl("First done")
            }
            launch {
                delay(700)
                outl("Second done")
            }
            outl("Done with code")
        }
    }








    fun stillAwaiting() {
        clear("Prove awaiting children: ")
        val job = customScope.launch {
            launch(Dispatchers.IO) {
                delay(2100)
                outp("First done in $threadName")
            }
            launch {
                delay(700)
                outp("Second done in $threadName")
            }
        }
        job.invokeOnCompletion {
            outp("Coroutine done")
        }
    }
    // Add threadName
    // Explain scope inheritance + overriding








    fun awaitingDifferentScopes() {
        clear("Start awaiting")
        val job = customScope.launch {
            viewModelScope.launch {
                delay(1400)
                outp("First done")
            }
            launch {
                delay(700)
                outp("Second done")
            }
        }
        job.invokeOnCompletion {
            outp("Coroutine done")
        }
    }









    fun awaitingDifferentJobs() {
        clear("Start awaiting")
        val job = customScope.launch {
            launch(Job()) {
                delay(1400)
                outp("First done")
            }
            launch {
                delay(700)
                outp("Second done")
            }
        }
        job.invokeOnCompletion {
            outp("Coroutine done")
        }
    }






















    fun awaitingManualJobs() {
        clear("Start awaiting")
        val customJob = Job()
        val jobOut = customScope.launch(customJob) {
            launch {
                delay(1400)
                outp("First done")
            }
            launch {
                delay(700)
                outp("Second done")
            }
        }
        customJob.invokeOnCompletion {
            outp("Custom Job Done")
        }
        jobOut.invokeOnCompletion {
            outp("Job Out Done")
        }
    }
}
package com.sample.crackingcoroutines.helpers

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.channelFlow

open class BaseViewModel : ViewModel() {
    val output = MutableLiveData("")
    val outList: MutableList<String> = mutableListOf()

    fun clear(value: String = "") {
        output.value = value
        outList.clear()
        if(value.isNotBlank()) {
            outList.add(value)
        }
    }
    @Synchronized
    protected fun out(any: Any) {
        outList.add(any.toString())
        output.value = outList.joinToString(", ")
    }
    @Synchronized
    protected fun outl(any: Any) {
        outList.add(any.toString())
        output.value = outList.joinToString("\n")
    }
    @Synchronized
    fun outp(any: Any) {
        outList.add(any.toString())
        output.postValue(outList.joinToString("\n"))
    }
}
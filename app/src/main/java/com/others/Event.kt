package com.others

open class Event<out T>(private val data:T) {
var hasBeenHandled =false
    private set

    fun getContentIfNOtHandled():T?{
        return if (hasBeenHandled){
            null
        }else{
            hasBeenHandled =true
            data
        }
    }

    fun peekContent() = data
}
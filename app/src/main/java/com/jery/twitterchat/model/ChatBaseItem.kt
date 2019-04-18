package com.jery.twitterchat.model

abstract class ChatBaseItem <T> (type : Int, time : Long) {
    companion object {
        const val TYPE_TEXT = 1
    }
    var type = type
    var time = time
    abstract fun getContent() : T
}
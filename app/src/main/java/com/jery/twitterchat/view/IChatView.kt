package com.jery.twitterchat.view

import com.jery.twitterchat.model.ChatBaseItem
interface IChatView {
    fun updateChat(msgs : List<ChatBaseItem<Any>>)
    fun updateChat(msg : ChatBaseItem<Any>)
    fun onInputInvalid(errMsg : String)
    fun onSendChatSuccess()
}
package com.jery.twitterchat.presenter
import com.jery.twitterchat.model.ChatBaseItem
import com.jery.twitterchat.model.ChatTextItem
import com.jery.twitterchat.view.IChatView

class ChatPresenter(view : IChatView) {

    private var view = view

    fun processSendText(text : String) {
        if(ChatTextItem.needSplitMsg(text)) {
            var msgList = ChatTextItem.splitMsg(text)
            print("msgList: " + msgList?.size)
            if(msgList != null) {
                view.updateChat(msgs = msgList as List<ChatBaseItem<Any>>)
                view.onSendChatSuccess()
            } else {
                view.onInputInvalid("Message too long!")
            }
        } else {
            var msg = ChatTextItem(System.currentTimeMillis(), text)
            view.updateChat(msg = msg as ChatBaseItem<Any>)
            view.onSendChatSuccess()
        }
    }
}
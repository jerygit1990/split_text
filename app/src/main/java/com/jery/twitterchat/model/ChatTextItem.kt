package com.jery.twitterchat.model

import com.jery.twitterchat.utils.ChatUtils

class ChatTextItem(time : Long, content : String) : ChatBaseItem<String>(TYPE_TEXT, time) {

    private var chatContent = content
    override fun getContent(): String {
        return chatContent
    }

    companion object {
        fun needSplitMsg(content: String) : Boolean {
            return content.length > ChatUtils.MAX_CHAR
        }

        fun splitMsg(content : String) : List<ChatTextItem>? {
            var listMsgs = ChatUtils.splitMsg(content)
            if(listMsgs != null) {
                var rs = ArrayList<ChatTextItem>()
                for (msg in listMsgs) {
                    rs.add(ChatTextItem(System.currentTimeMillis(), msg))
                }
                return rs
            }
            return null
        }
    }


}
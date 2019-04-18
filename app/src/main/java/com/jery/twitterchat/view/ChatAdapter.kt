package com.jery.twitterchat.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jery.twitterchat.R
import com.jery.twitterchat.model.ChatBaseItem
import com.jery.twitterchat.model.ChatTextItem
import kotlinx.android.synthetic.main.chat_item_type_text.view.*

class ChatAdapter(context : Context, msgs : List<ChatBaseItem<Any>>) : RecyclerView.Adapter<ChatAdapter.ChatBaseHolder>() {
    var chatList = msgs
    var context = context

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ChatBaseHolder {
        when(chatList[p1].type) {
            ChatBaseItem.TYPE_TEXT -> {
                return ChatTextHolder(
                    LayoutInflater.from(context).inflate(
                        R.layout.chat_item_type_text,
                        p0,
                        false
                    )
                )
            }
        }
        return ChatUnSupportHolder(
            LayoutInflater.from(p0.context).inflate(
                R.layout.chat_item_type_unsupport,
                p0,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun onBindViewHolder(p0: ChatBaseHolder, p1: Int) {
        p0.bindData(chatList[p1])
    }

    class ChatTextHolder(view : View) : ChatBaseHolder(view) {
        override fun bindData(msg: ChatBaseItem<Any>) {
            var chatData = msg as ChatTextItem
            chipMsg.text = chatData.getContent()
        }

        private var chipMsg = view.txt_msg
    }

    class ChatUnSupportHolder(view: View) : ChatBaseHolder(view) {
        override fun bindData(msg: ChatBaseItem<Any>) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    abstract class ChatBaseHolder(view : View) : RecyclerView.ViewHolder(view) {
        abstract fun bindData(msg : ChatBaseItem<Any>)
    }
}
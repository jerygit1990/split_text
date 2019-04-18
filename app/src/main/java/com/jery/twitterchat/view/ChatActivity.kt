package com.jery.twitterchat.view

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import com.jery.twitterchat.*
import com.jery.twitterchat.model.ChatBaseItem
import com.jery.twitterchat.model.ChatTextItem
import com.jery.twitterchat.presenter.ChatPresenter
import com.jery.twitterchat.utils.ChatUtils
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity(), IChatView {

    private var msgList = ArrayList<ChatBaseItem<Any>>()
    private var chatAdapter : ChatAdapter? = null
    private var chatPresenter = ChatPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        initUI()
    }

    private fun initUI() {
        toolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Best friend"

        chatAdapter = ChatAdapter(this, msgList)
        var layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        rv_chat.layoutManager = layoutManager
        rv_chat.adapter = chatAdapter

        btn_send_chat.setOnClickListener{
            chatPresenter.processSendText(edt_msg.text.toString())
        }

        edt_msg.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                btn_send_chat.isEnabled = p0 != null && p0.isNotEmpty()
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.chat_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete -> {
                msgList.clear()
                chatAdapter?.notifyDataSetChanged()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun updateChat(msg: ChatBaseItem<Any>) {
        msgList.add(msg)
        chatAdapter?.notifyDataSetChanged()
    }

    override fun updateChat(msgs: List<ChatBaseItem<Any>>) {
        msgList.addAll(msgs)
        chatAdapter?.notifyDataSetChanged()
    }

    override fun onInputInvalid(errMsg: String) {
        edt_msg.error = errMsg
        btn_send_chat.isEnabled = false
    }

    override fun onSendChatSuccess() {
        edt_msg.text.clear()
    }
}
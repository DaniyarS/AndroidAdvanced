package com.daniyars.translatorapp.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.daniyars.translatorapp.R
import com.daniyars.translatorapp.models.MessageData
import com.daniyars.translatorapp.utils.MessageDiffCallback
import com.daniyars.translatorapp.utils.MessagePayload
import kotlinx.android.synthetic.main.message_items.view.*

class MessageAdapter : RecyclerView.Adapter<MessageAdapter.ViewHolder>() {

    private val message = mutableListOf<MessageData>()
    private var context: Context? = null
    private val diffCallback =
        MessageDiffCallback()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        return ViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = message.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(message[position])
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            holder.onBind(message[position], payloads.first() as? Set<*>)
        }
    }

    fun setItemsWithDiffCallback(list: List<MessageData>) {
        diffCallback.setItems(message, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback, false)
        message.clear()
        message.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        View.OnCreateContextMenuListener,
        RecyclerView.ViewHolder(
            inflater.inflate(
                R.layout.message_items, parent, false
            )
        ) {

        private val tvInsert = itemView.insertTextView
        private val tvResult = itemView.resultTextView

        fun bind(message: MessageData) {
            tvInsert.text = message.sendedMessage
            tvResult.text = message.receivedMessage

            tvInsert.setOnCreateContextMenuListener(this)
            tvResult.setOnCreateContextMenuListener(this)
        }

        fun onBind(message: MessageData, fields: Set<*>?) {
            fields?.forEach {
                when (it) {
                    MessagePayload.SEND_MESSAGE -> updateSendedMessage(message.sendedMessage)
                    MessagePayload.RECEIVED_MESSAGE -> updateReceivedMessage(message.receivedMessage)
                }
            }
        }

        private fun updateSendedMessage(sended: String?) {
            tvInsert.text = sended
        }

        private fun updateReceivedMessage(received: String?) {
            tvResult.text = received
        }

        @SuppressLint("RestrictedApi")
        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            val popupMenu = PopupMenu(context, v)
            popupMenu.inflate(R.menu.message_items)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                popupMenu.setForceShowIcon(true)
                popupMenu.show()
            } else {
                val popupHelper = MenuPopupHelper(
                    context!!,
                    popupMenu.menu as MenuBuilder,
                    v!!,
                    false,
                    R.attr.popupMenuStyle,
                    0
                )
                popupHelper.setForceShowIcon(true)
                popupHelper.show()
            }
        }
    }
}
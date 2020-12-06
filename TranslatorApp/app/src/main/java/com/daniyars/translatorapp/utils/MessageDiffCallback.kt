package com.daniyars.translatorapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.daniyars.translatorapp.models.MessageData

class MessageDiffCallback : DiffUtil.Callback() {

    private var oldList = emptyList<MessageData>()
    private var newList = emptyList<MessageData>()

    fun setItems(oldList: List<MessageData>, newList: List<MessageData>) {
        this.oldList = oldList
        this.newList = newList
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].sendedMessage.toString() == newList[newItemPosition].sendedMessage.toString()

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return oldList[oldPosition] == newList[newPosition]
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val fields = mutableListOf<MessagePayload>()
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        if (oldItem.sendedMessage != newItem.sendedMessage) fields.add(MessagePayload.SEND_MESSAGE)
        if (oldItem.receivedMessage != newItem.receivedMessage) fields.add(MessagePayload.RECEIVED_MESSAGE)

        return when {
            fields.isNotEmpty() -> fields
            else -> super.getChangePayload(oldItemPosition, newItemPosition)
        }
    }
}

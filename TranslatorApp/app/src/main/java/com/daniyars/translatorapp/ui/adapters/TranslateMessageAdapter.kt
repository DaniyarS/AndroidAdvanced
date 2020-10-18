package com.daniyars.translatorapp.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daniyars.translatorapp.R
import com.daniyars.translatorapp.models.TranslateMessageData
import kotlinx.android.synthetic.main.translate_message_items.view.*

class TranslateMessageAdapter(var listOfMessages: MutableList<TranslateMessageData>) : RecyclerView.Adapter<TranslateMessageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return listOfMessages.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOfMessages[position])
    }

    inner class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(
        R.layout.translate_message_items, parent, false)) {
        private val insertTextView = itemView.insertTextView
        private val resultTextView = itemView.resultTextView
        fun bind(message: TranslateMessageData) {
            insertTextView.text = message.insertMessage
            resultTextView.text = message.resultMessage
        }
    }
}
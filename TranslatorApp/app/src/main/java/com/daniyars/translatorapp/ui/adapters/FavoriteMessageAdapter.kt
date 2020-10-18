package com.daniyars.translatorapp.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daniyars.translatorapp.R
import com.daniyars.translatorapp.models.MessageData
import com.daniyars.translatorapp.ui.ItemMoveCallbackListener
import com.daniyars.translatorapp.ui.OnStartDragListener
import kotlinx.android.synthetic.main.favorite_message_items.view.*
import java.util.*

class FavoriteMessageAdapter(private val startDragListener: OnStartDragListener, private val clickListener: (position: Int) -> Unit) : RecyclerView.Adapter<FavoriteMessageAdapter.ViewHolder>(),
    ItemMoveCallbackListener.Listener {

    private val message = mutableListOf<MessageData>()
    private var context: Context? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        return ViewHolder(inflater, parent)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(message[position], clickListener)
        holder.itemView.btDrag.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                this.startDragListener.onStartDrag(holder)
            }
            return@setOnTouchListener true
        }
    }

    override fun getItemCount(): Int = message.size

    override fun onRowMoved(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(message, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(message, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onRowSelected(itemViewHolder: ViewHolder) {
    }

    override fun onRowClear(itemViewHolder: ViewHolder) {
    }

    fun setItems(list: List<MessageData>) {
        message.addAll(list)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        message.removeAt(position)
        notifyDataSetChanged()
    }

    inner class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.favorite_message_items, parent, false)) {
        private val tvInsert = itemView.tvSend
        private val tvResult = itemView.tvReceived
        private val btDelete = itemView.btDelete

        fun bind(message: MessageData, clickListener: (position: Int) -> Unit) {
            tvInsert.text = message.sendedMessage
            tvResult.text = message.receivedMessage

            btDelete.setOnClickListener {
                clickListener(adapterPosition)
            }
        }
    }
}

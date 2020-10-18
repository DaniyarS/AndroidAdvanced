package com.daniyars.translatorapp.ui

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.daniyars.translatorapp.ui.adapters.FavoriteMessageAdapter

class ItemMoveCallbackListener(private val adapter: FavoriteMessageAdapter) : ItemTouchHelper.Callback() {
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        return makeMovementFlags(dragFlags, 0)
    }
    override fun isItemViewSwipeEnabled(): Boolean {
        return false
    }
    override fun isLongPressDragEnabled(): Boolean {
        return false
    }
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        adapter.onRowMoved(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }
    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder is FavoriteMessageAdapter.ViewHolder) {
                adapter.onRowSelected(viewHolder)
            }
        }
        super.onSelectedChanged(viewHolder, actionState)
    }
    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        if (viewHolder is FavoriteMessageAdapter.ViewHolder) {
            adapter.onRowClear(viewHolder)
        }
    }
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    }
    interface Listener {
        fun onRowMoved(fromPosition: Int, toPosition: Int)
        fun onRowSelected(itemViewHolder: FavoriteMessageAdapter.ViewHolder)
        fun onRowClear(itemViewHolder: FavoriteMessageAdapter.ViewHolder)
    }
}
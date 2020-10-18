package com.daniyars.translatorapp.ui.mainNavFragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daniyars.translatorapp.R
import com.daniyars.translatorapp.models.MessageData
import com.daniyars.translatorapp.ui.ItemMoveCallbackListener
import com.daniyars.translatorapp.ui.OnStartDragListener
import com.daniyars.translatorapp.ui.adapters.FavoriteMessageAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : Fragment(R.layout.fragment_favorites), OnStartDragListener {
    private lateinit var recyclerView: RecyclerView
    private var favoriteMessageAdapter: FavoriteMessageAdapter? = null
    private var message = mutableListOf<MessageData>()
    private lateinit var touchHelper: ItemTouchHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView = view.findViewById(R.id.favoriteRecycler)
        recyclerView.layoutManager = linearLayoutManager
        favoriteMessageAdapter = FavoriteMessageAdapter(this) { position ->
            favoriteMessageAdapter?.removeItem(position)
            Snackbar.make(favoriteContainer, R.string.snackbar, 3000).setAction(R.string.snackbar_action) {
            }.show()
        }
        recyclerView.adapter = favoriteMessageAdapter
        updateFavorite()
        favoriteMessageAdapter?.let {
            val callback: ItemTouchHelper.Callback = ItemMoveCallbackListener(it)
            touchHelper = ItemTouchHelper(callback)
            touchHelper.attachToRecyclerView(recyclerView)
        }
    }

    override fun onStartDrag(viewHolder: RecyclerView.ViewHolder?) {
        if (viewHolder != null) {
            touchHelper.startDrag(viewHolder)
        }
    }

    private fun updateFavorite() {
        val msg1 = "Абай (Ибраһим) Құнанбаев (1845-1904) — ақын, ағартушы, жазба қазақ әдебиетінің," +
                " қазақ әдеби тілінің негізін қалаушы, философ, композитор"
        val msg2 = "Abai (Ibrahim) Kunanbayev (1845-1904) - poet, educator, founder of written Kazakh" +
                " literature, Kazakh literary language, philosopher, composer"
        val msg3 = "Сәлем, қалайсың?"
        val msg4 = "Hello, how are you?"
        val msg5 = "Абай (Ибраһим) Құнанбаев (1845-1904) — ақын, ағартушы, жазба қазақ әдебиетінің," +
                " қазақ әдеби тілінің негізін қалаушы, философ, композитор"
        val msg6 = "Abai (Ibrahim) Kunanbayev (1845-1904) - poet, educator, founder of written Kazakh" +
                " literature, Kazakh literary language, philosopher, composer"

        message = mutableListOf(
            MessageData(msg1, msg2),
            MessageData(msg3, msg4),
            MessageData(msg5, msg6)
        )

        favoriteMessageAdapter?.setItems(message)
    }
}
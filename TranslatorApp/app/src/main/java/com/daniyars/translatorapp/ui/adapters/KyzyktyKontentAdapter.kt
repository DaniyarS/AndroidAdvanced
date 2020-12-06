package com.daniyars.translatorapp.ui.adapters

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.toSpannable
import androidx.recyclerview.widget.RecyclerView
import com.daniyars.translatorapp.R
import com.daniyars.translatorapp.models.VideoObjects
import com.daniyars.translatorapp.ui.custom.VideoPlayerView
import kotlinx.android.synthetic.main.search_fragment_items.view.*
import kotlinx.android.synthetic.main.view_videplayer.view.*

class KyzyktyKontentAdapter(private val callback: ((String) -> Unit)? = null) :
    RecyclerView.Adapter<KyzyktyKontentAdapter.ViewHolder>() {

    private var context: Context? = null
    // private val items = DataSource.items
    private var search: String? = null
    private val videos = mutableListOf<VideoObjects>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): KyzyktyKontentAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        return ViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = videos.size

    override fun onBindViewHolder(holder: KyzyktyKontentAdapter.ViewHolder, position: Int) {
        holder.bind(videos[position])
        holder.itemView.playButton.setOnClickListener {
            callback?.invoke(videos[position].videoURL)
        }
    }

    fun setItems(list: List<VideoObjects>) {
        videos.clear()
        videos.addAll(list)
        notifyDataSetChanged()
    }

    fun setFilteredItems(list: List<VideoObjects>, text: String) {
        videos.clear()
        videos.addAll(list)
        search = text
        notifyDataSetChanged()
    }

    inner class ViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(
        inflater.inflate(R.layout.search_fragment_items, parent, false)
    ) {
        private val videoPlayerView: VideoPlayerView = itemView.videoPlayerView
        fun bind(post: VideoObjects) {

            val searchText = search
            if (searchText != null && post.title.haveText(searchText)
            ) {
                val spanText = post.title.toSpannable()
                spanText.setSpan(
                    ForegroundColorSpan(Color.rgb(23, 137, 219)),
                    matchDetails(post.title, searchText),
                    matchDetails(post.title, searchText) + searchText.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                videoPlayerView.titleTextView.text = spanText
            } else
                videoPlayerView.titleTextView.text = post.title
        }

        private fun matchDetails(inputString: String, whatToFind: String): Int {
            return inputString.indexOf(whatToFind, 0)
        }
    }
}

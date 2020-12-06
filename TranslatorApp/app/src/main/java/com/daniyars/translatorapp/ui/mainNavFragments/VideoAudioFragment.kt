package com.daniyars.translatorapp.ui.mainNavFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.daniyars.translatorapp.R
import com.daniyars.translatorapp.models.DataSource
import com.daniyars.translatorapp.models.VideoObjects
import com.daniyars.translatorapp.ui.adapters.KyzyktyKontentAdapter
import com.daniyars.translatorapp.ui.custom.VideoFragment
import kotlinx.android.synthetic.main.fragment_video_audio.*
import java.util.*

class VideoAudioFragment : Fragment() {
    private var kyzyktyKontentAdapter: KyzyktyKontentAdapter? = null
    private val items = DataSource.items

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_video_audio, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        kyzyktyKontentAdapter = KyzyktyKontentAdapter(callback = { replaceVideoFragment(it) })

        val linearLayoutManager = LinearLayoutManager(activity?.applicationContext)
        searchRecycler.apply {
            kyzyktyKontentAdapter?.setItems(items)
            adapter = kyzyktyKontentAdapter
            layoutManager = linearLayoutManager
        }

        editText2.addTextChangedListener {
            filter(editText2.text.toString().toLowerCase(Locale.ROOT))
            if (editText2.text.isEmpty()) {
                kyzyktyKontentAdapter?.setItems(items)
            }
        }
    }

    private fun filter(query: String) {
        for (item in items.indices) {
            val filteredList = mutableListOf<VideoObjects>()
            val title = items[item].title.toLowerCase(Locale.ROOT)
            if (title.contains(query)) {
                filteredList.add(items[item])
                kyzyktyKontentAdapter?.setFilteredItems(filteredList, query)
            }
        }
    }

    private fun replaceVideoFragment(url: String) {
        fragmentManager?.beginTransaction()
            ?.replace(R.id.fragmentContainer, VideoFragment.newInstance(url))
            ?.commit()
    }
}
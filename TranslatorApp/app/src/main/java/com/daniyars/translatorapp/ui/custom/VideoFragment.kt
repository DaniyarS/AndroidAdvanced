package com.daniyars.translatorapp.ui.custom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import com.daniyars.translatorapp.R
import com.daniyars.translatorapp.ui.activities.MainActivity
import kotlinx.android.synthetic.main.fragment_video.*
import kotlinx.android.synthetic.main.fragment_video_audio.*
import kotlinx.android.synthetic.main.search_fragment_items.videoPlayerView
import kotlin.math.abs

private const val URL = "url"

class VideoFragment : Fragment(R.layout.fragment_video) {

    companion object {
        fun newInstance(url: String): VideoFragment = VideoFragment().also { f ->
            f.arguments = Bundle().also { b ->
                b.putString(URL, url)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_video, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val url = arguments?.getString(URL)
        if (url != null) {
            videoPlayerView.play(url)
        }

        videoMotionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionChange(motionLayout: MotionLayout?, startId: Int, endId: Int, progress: Float) {
                (activity as MainActivity).also {
                    it.mainMotionLayout.progress = abs(progress)
                }
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
            }

            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
            }
        })
        videoMotionLayout.transitionToEnd()
    }
}
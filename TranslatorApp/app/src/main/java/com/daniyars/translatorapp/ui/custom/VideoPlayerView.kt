package com.daniyars.translatorapp.ui.custom

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.SeekBar
import androidx.core.view.isVisible
import com.daniyars.translatorapp.R
import kotlinx.android.synthetic.main.view_videplayer.view.*

class VideoPlayerView
@JvmOverloads constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr) {

    private var isSeekBarVisible = false
    private var isPlayButtonVisible = true
    private var isPauseButtonVisible = false
    private var isTitleTextViewVisible = true
    private var isTimeTextViewVisible = true
    private var isViewBackVisible = true
    private lateinit var runnable: Runnable
    private val handlerView: Handler? = Handler()

    init {
        inflate(context, R.layout.view_videplayer, this)
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(
                it,
                R.styleable.VideoPlayerView,
                defStyleAttr,
                0
            )
            isSeekBarVisible = typedArray.getBoolean(
                R.styleable.VideoPlayerView_isSeekBarVisible,
                isSeekBarVisible
            )

            isTitleTextViewVisible = typedArray.getBoolean(
                R.styleable.VideoPlayerView_isTitleTextViewVisible,
                isTitleTextViewVisible
            )

            isViewBackVisible = typedArray.getBoolean(
                R.styleable.VideoPlayerView_isViewBackVisible,
                isViewBackVisible
            )

            seekBar.isVisible = isSeekBarVisible
            playButton.isVisible = isPlayButtonVisible
            pauseButton.isVisible = isPauseButtonVisible
            titleTextView.isVisible = isTitleTextViewVisible
            timeTextView.isVisible = isTimeTextViewVisible
            typedArray.recycle()
        }
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                seekBar?.progress = videoView.currentPosition
                timeTextView.text = "${seekBar?.progress} - ${videoView.duration}"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                videoView.seekTo(seekBar?.progress ?: 0)
                videoView.start()
                timeTextView.text = "${seekBar?.progress}" + "${videoView.duration}"
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                videoView.seekTo(seekBar?.progress ?: 0)
                videoView.start()
            }
        })
    }

    fun play(url: String) {
        videoView.setVideoPath(url/*context.assets.open("sample.mp4").toString()/*path*/*/)
        videoView.start()
        playButton.isVisible = false
        videoView.setOnPreparedListener {
            seekBar.max = videoView.duration
            listenPlayerTrack()
        }
    }

    private fun listenPlayerTrack() {
        runnable = Runnable {
            seekBar.progress = videoView.currentPosition
            handlerView?.postDelayed(runnable, 100)
        }
        handlerView?.postDelayed(runnable, 100)
    }
}

package com.daniyars.translatorapp.ui.mainNavFragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.daniyars.translatorapp.R
import com.daniyars.translatorapp.local.LocalPreferences
import com.daniyars.translatorapp.local.MessageDao
import com.daniyars.translatorapp.local.MessageDatabase
import com.daniyars.translatorapp.models.MessageData
import com.daniyars.translatorapp.ui.MessageItemDecoration
import com.daniyars.translatorapp.ui.activities.MainActivity
import com.daniyars.translatorapp.ui.adapters.MessageAdapter
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_kir_lat.*
import kotlinx.coroutines.*
import java.lang.Runnable
import kotlin.coroutines.CoroutineContext

class KirLatFragment : Fragment(), CoroutineScope {
    private lateinit var btSend: ImageView
    private lateinit var tvEdit: EditText
    private var messages = mutableListOf<MessageData>()
    private var messageAdapter: MessageAdapter? = null
    private var messageLayoutManager: LinearLayoutManager? = null
    private val scrollListener = object : RecyclerView.OnScrollListener() {
        var y = 0

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            y = dy
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            when (newState) {
                RecyclerView.SCROLL_STATE_DRAGGING -> {
                    if (y <= 0) {
                        scrollButton.visibility = View.VISIBLE
                    } else {
                        scrollButton.visibility = View.GONE
                        y = 0
                    }
                }
            }
        }
    }

    private lateinit var translateThread: TranslateThread
    private var uiHandler: Handler? = Handler()
    private lateinit var runnable: Runnable
    private val disposable = CompositeDisposable()

    private val job = Job()
    private var messageDao: MessageDao? = null

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_kir_lat, container, false)

        btSend = view.findViewById(R.id.btSend)!!
        tvEdit = view.findViewById(R.id.editText)!!
        messageDao = context?.let { MessageDatabase.getDatabase(it).messageDao() }

        return view
    }

    @SuppressLint("RestrictedApi", "ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        translateThread = TranslateThread()
        themeButton.setOnClickListener {
            val themePreferences = context?.let { it1 -> LocalPreferences(it1) }
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                themePreferences?.setThemeState("dark")
                themePreferences?.setIntent("main")
                restartApp()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                restartApp()
                themePreferences?.setThemeState("white")
            }
        }

        moreButton.setOnClickListener { v ->
            val popupMenu = PopupMenu(context, v)
            popupMenu.inflate(R.menu.toolbar_items)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                popupMenu.setForceShowIcon(true)
                popupMenu.gravity = Gravity.RELATIVE_LAYOUT_DIRECTION
                popupMenu.show()
            } else {
                val popupHelper = MenuPopupHelper(
                    requireContext(),
                    popupMenu.menu as MenuBuilder,
                    v,
                    false,
                    R.attr.popupMenuStyle,
                    0
                )
                popupHelper.setForceShowIcon(true)
                popupHelper.show()
            }
        }

        updateMessages()
    }

    override fun onResume() {
        super.onResume()
        messageRecycler.addOnScrollListener(scrollListener)
    }

    override fun onDestroy() {
        super.onDestroy()
        translateThread.interrupt()
    }

    private fun updateMessages() {
        messageAdapter = MessageAdapter()
        messageLayoutManager =
            LinearLayoutManager(activity?.baseContext, LinearLayoutManager.VERTICAL, false)
        messageLayoutManager!!.stackFromEnd = true
        messageRecycler.adapter = messageAdapter
        messageRecycler.layoutManager = messageLayoutManager

        launch {
            val list = withContext(Dispatchers.IO) {
                messageDao?.getMessage()
            }

            if (list != null) {
                messages.addAll(list)
            }

            messageAdapter?.setItemsWithDiffCallback(messages)
        }

        val smoothScroller = object : LinearSmoothScroller(context) {
            override fun getVerticalSnapPreference(): Int =
                SNAP_TO_START
        }

        uiHandler?.post {
            translateThread.start()
        }.also { disposable }

        btSend.setOnClickListener {

            if (tvEdit.text.toString() != "") {
                uiHandler?.post {
                    runnable = Runnable {
                        val message = MessageData()
                        message.sendedMessage = tvEdit.text.toString()
                        message.receivedMessage = changeToLatin(tvEdit.text.toString())

                        val sharedPreferences = context?.let { LocalPreferences(it) }

                        if (sharedPreferences?.getLogState() == true) {
                            val words = tvEdit.text.toString().replace("\n", " ").split(" ").size
                            Log.d("parts", words.toString())
                            val totalCount = sharedPreferences.getTotalWords()?.toInt()?.plus(words)
                            sharedPreferences.setTotalWords(totalCount.toString())
                        }

                        tvEdit.text = null
                        launch {
                            withContext(Dispatchers.IO) {
                                messageDao?.insertMessage(message)
                            }

                            val list = withContext(Dispatchers.IO) {
                                messageDao?.getMessage()
                            }

                            if (list != null) {
                                messages.clear()
                                messages.addAll(list)
                            }
                            smoothScroller.targetPosition = messages.size - 1
                            messageLayoutManager!!.startSmoothScroll(smoothScroller)
                            messageAdapter?.setItemsWithDiffCallback(messages)
                        }
                    }
                    translateThread.prepareHandler()
                    translateThread.postTask(runnable)
                }.also { disposable }
            }
        }

        scrollButton.setOnClickListener {
            if (messages.size > 50) {
                messageLayoutManager!!.scrollToPosition(messages.size - 5)
                smoothScroller.targetPosition = messages.size - 1
                messageLayoutManager!!.startSmoothScroll(smoothScroller)
            } else {
                smoothScroller.targetPosition = messages.size - 1
                messageLayoutManager!!.startSmoothScroll(smoothScroller)
            }
            scrollButton.visibility = View.GONE
        }

        val itemDecoration =
            MessageItemDecoration(10, 20)
        messageRecycler.addItemDecoration(itemDecoration)
    }

    private fun changeToLatin(text: String): String {
        val message = StringBuilder()

        val cyrillic: Array<Char> = arrayOf(' ', 'а', 'б', 'в', 'г', 'д', 'е', 'ё', 'ж', 'з', 'и', 'й', 'к',
            'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю',
            'я', 'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И', 'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 'Т', 'У',
            'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ъ', 'Ы', 'Ь', 'Э', 'Ю', 'Я', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
            'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z')

        val latin: Array<String> = arrayOf(" ", "a", "b", "v", "g", "d", "e", "e", "zh", "z", "i", "y", "k", "l",
            "m", "n", "o", "p", "r", "s", "t", "u", "f", "h", "ts", "ch", "sh", "sch", "", "i", "", "e", "ju", "ja",
            "A", "B", "V", "G", "D", "E", "E", "Zh", "Z", "I", "Y", "K", "L", "M", "N", "O", "P", "R", "S", "T", "U", "F",
            "H", "Ts", "Ch", "Sh", "Sch", "", "I", "", "E", "Ju", "Ja", "a", "b", "c", "d", "e", "f", "g", "h", "i",
            "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")

        for (i in text.indices) {
            for (j in cyrillic.indices) {
                Log.d("size", cyrillic.size.toString())
                if (text[i] == (cyrillic[j])) {
                    message.append(latin[j])
                }
            }
        }
        return message.toString()
    }

    private fun restartApp() {
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
    }
}

class TranslateThread : Thread() {
    private var translateHandler = Handler()
    fun postTask(task: Runnable) {
        translateHandler.post(task)
    }

    fun prepareHandler() {
        translateHandler = Handler(Looper.myLooper()!!)
    }
}
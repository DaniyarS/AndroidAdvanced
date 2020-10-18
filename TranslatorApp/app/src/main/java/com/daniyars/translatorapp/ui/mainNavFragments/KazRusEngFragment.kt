package com.daniyars.translatorapp.ui.mainNavFragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.daniyars.translatorapp.R
import com.daniyars.translatorapp.api.YandexTranslateApiService
import com.daniyars.translatorapp.local.MessageDatabase
import com.daniyars.translatorapp.local.TranslateMessageDao
import com.daniyars.translatorapp.models.TranslateMessageData
import com.daniyars.translatorapp.ui.adapters.TranslateMessageAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_kaz_rus_eng.*

private const val YANDEX_TRANSLATE_API_TOKEN =
    "trnsl.1.1.20200524T102729Z.ade3b538609460ba.45635739d3950dc38b85595992876b142f31c4f2"
class KazRusEngFragment : Fragment(R.layout.fragment_kaz_rus_eng) {

    private var listOfMessages = mutableListOf<TranslateMessageData>()
    private var translateMessageAdapter: TranslateMessageAdapter? = null
    private val yandexTranslateApiService = YandexTranslateApiService.getYandexApi()
    private val disposable = CompositeDisposable()
    private var messageDao: TranslateMessageDao? = null

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        menuOptions.setOnCreateContextMenuListener(this)

        messageDao = context?.let { MessageDatabase.getDatabase(it).translateMessageDao() }

        switchButton.setOnClickListener {
            when (menuOptions.text) {
                "Қаз - Eng" -> {
                    menuOptions.text = "Eng - Қаз"
                }
                "Eng - Қаз" -> {
                    menuOptions.text = "Қаз - Eng"
                }
                "Рус - Eng" -> {
                    menuOptions.text = "Eng - Рус"
                }
                else -> {
                    menuOptions.text = "Рус - Eng"
                }
            }
        }

        initComponents()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu.add(Menu.NONE, 11, Menu.NONE, "Қаз - Eng")
        menu.add(Menu.NONE, 12, Menu.NONE, "Eng - Қаз")
        menu.add(Menu.NONE, 13, Menu.NONE, "Рус - Eng")
        menu.add(Menu.NONE, 14, Menu.NONE, "Eng - Рус")
    }

    @SuppressLint("SetTextI18n")
    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            11 -> {
                menuOptions.text = "Қаз - Eng"
            }
            12 -> {
                menuOptions.text = "Eng - Қаз"
            }
            13 -> {
                menuOptions.text = "Рус - Eng"
            }
            14 -> {
                menuOptions.text = "Eng - Рус"
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

    private fun initComponents() {
        translateMessageAdapter =
            activity?.applicationContext?.let { TranslateMessageAdapter(listOfMessages) }
        val translateMessageLayoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        translateMessageLayoutManager.stackFromEnd = true
        translateRecycler.layoutManager = translateMessageLayoutManager
        translateRecycler.adapter = translateMessageAdapter
        getMessagesFromDB()
        sendButton.setOnClickListener {
            if (editText.text.toString() != "") {
                saveMessageToDB()
                progressBar.visibility = View.VISIBLE
            }
        }
    }

    private fun saveMessageToDB() {
        var langCode = ""
        when (menuOptions.text.toString()) {
            "Қаз - Eng" -> {
                langCode = "kk-en"
            }
            "Eng - Қаз" -> {
                langCode = "en-kk"
            }
            "Рус - Eng" -> {
                langCode = "ru-en"
            }
            "Eng - Рус" -> {
                langCode = "en-ru"
            }
        }

        yandexTranslateApiService.getTranslationResult(
            YANDEX_TRANSLATE_API_TOKEN,
            editText.text.toString(),
            langCode
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    val messageData = TranslateMessageData()
                    messageData.insertMessage = editText.text.toString()
                    messageData.resultMessage = it.text[0]
                    messageDao?.insertMessage(messageData)
                    progressBar.visibility = View.GONE
                    editText.text = null
                },
                {
                    Log.d(it.message.toString(), "Error get translation")
                    progressBar.visibility = View.GONE
                }
            )
            .also { disposable }
    }

    private fun getMessagesFromDB() {
        messageDao?.getAllMessages()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                {
                    listOfMessages.clear()
                    listOfMessages.addAll(it)
                    translateMessageAdapter?.listOfMessages = listOfMessages
                    translateMessageAdapter?.notifyDataSetChanged()
                },
                {
                    Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
                }
            )
            .also { disposable }
    }
}
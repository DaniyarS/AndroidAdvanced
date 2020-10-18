package com.daniyars.translatorapp.models

data class TranslateMessageResponse(
    val code: Int,
    val lang: String,
    val text: List<String>
)
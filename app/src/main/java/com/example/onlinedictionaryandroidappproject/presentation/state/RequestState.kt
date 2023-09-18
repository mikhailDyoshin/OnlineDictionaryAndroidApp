package com.example.onlinedictionaryandroidappproject.presentation.state

data class RequestState(
    val statusMessage: String? = null,
    var audioStatusMessage: String? = null,
    val wordsStatesList: List<WordState> = listOf()
)

package com.example.onlinedictionaryandroidappproject.presentation

data class RequestState(
    val statusMessage: String? = null,
    val wordsStatesList: List<WordState> = listOf()
)

package com.example.onlinedictionaryandroidappproject.presentation.state

data class WordState(
    var word: String? = null,
    var meanings: List<MeaningsState> = listOf(),
)

package com.example.onlinedictionaryandroidappproject.presentation

import com.example.onlinedictionaryandroidappproject.domain.models.MeaningsDomainModel

data class WordState(
    var word: String? = null,
    var meanings: MutableList<MeaningsState> = mutableListOf(),
)

package com.example.onlinedictionaryandroidappproject.domain.models


data class WordDomainModel(
    var word: String? = null,
    var meanings: List<MeaningsDomainModel> = listOf(),
)

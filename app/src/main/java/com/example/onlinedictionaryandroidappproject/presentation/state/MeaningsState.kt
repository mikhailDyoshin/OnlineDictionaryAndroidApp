package com.example.onlinedictionaryandroidappproject.presentation.state

data class MeaningsState(
    var partOfSpeech: String? = null,
    var definitions: List<DefinitionsState> = listOf(),
    var synonyms: List<String> = listOf(),
    var antonyms: List<String> = listOf()
)

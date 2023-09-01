package com.example.onlinedictionaryandroidappproject.presentation.state

data class MeaningsState(
    var partOfSpeech: String? = null,
    var definitions: MutableList<DefinitionsState> = mutableListOf(),
    var synonyms: MutableList<String> = mutableListOf(),
    var antonyms: MutableList<String> = mutableListOf()
)

package com.example.onlinedictionaryandroidappproject.presentation

data class DefinitionsState(
    var definition: String? = null,
    var synonyms: MutableList<String> = mutableListOf(),
    var antonyms: MutableList<String> = mutableListOf(),
    var example: String? = null
)

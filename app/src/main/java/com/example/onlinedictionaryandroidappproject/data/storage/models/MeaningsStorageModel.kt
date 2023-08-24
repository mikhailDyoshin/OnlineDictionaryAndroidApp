package com.example.onlinedictionaryandroidappproject.data.storage.models


data class MeaningsStorageModel(
    var partOfSpeech: String? = null,
    var definitions: MutableList<DefinitionsStorageModel> = mutableListOf(),
    var synonyms: MutableList<String> = mutableListOf(),
    var antonyms: MutableList<String> = mutableListOf()
)

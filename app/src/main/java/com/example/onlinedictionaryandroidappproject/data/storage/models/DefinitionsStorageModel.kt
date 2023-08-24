package com.example.onlinedictionaryandroidappproject.data.storage.models


data class DefinitionsStorageModel(
    var definition: String? = null,
    var synonyms: MutableList<String> = mutableListOf(),
    var antonyms: MutableList<String> = mutableListOf(),
    var example: String? = null
)

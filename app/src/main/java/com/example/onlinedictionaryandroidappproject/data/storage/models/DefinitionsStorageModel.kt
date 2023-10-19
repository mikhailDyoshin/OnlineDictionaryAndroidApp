package com.example.onlinedictionaryandroidappproject.data.storage.models


data class DefinitionsStorageModel(
    var definition: String? = null,
    var synonyms: List<String> = listOf(),
    var antonyms: List<String> = listOf(),
    var example: String? = null
)

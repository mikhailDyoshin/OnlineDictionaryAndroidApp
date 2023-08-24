package com.example.onlinedictionaryandroidappproject.domain.models

data class DefinitionsDomainModel(
    var definition: String? = null,
    var synonyms: MutableList<String> = mutableListOf(),
    var antonyms: MutableList<String> = mutableListOf(),
    var example: String? = null
)

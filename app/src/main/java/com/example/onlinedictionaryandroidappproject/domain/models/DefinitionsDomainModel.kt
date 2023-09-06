package com.example.onlinedictionaryandroidappproject.domain.models

data class DefinitionsDomainModel(
    var definition: String? = null,
    var synonyms: List<String> = listOf(),
    var antonyms: List<String> = listOf(),
    var example: String? = null
)

package com.example.onlinedictionaryandroidappproject.data.storage.models


data class WordStorageModel(
    var word: String? = null,
    var phonetics: List<PhoneticsStorageModel> = listOf(),
    var meanings: List<MeaningsStorageModel> = listOf(),
    var license: LicenseStorageModel? = LicenseStorageModel(),
    var sourceUrls: List<String> = listOf()
)

package com.example.onlinedictionaryandroidappproject.data.storage.models


data class WordStorageModel(
    var word: String? = null,
    var phonetics: MutableList<PhoneticsStorageModel> = mutableListOf(),
    var meanings: MutableList<MeaningsStorageModel> = mutableListOf(),
    var license: LicenseStorageModel? = LicenseStorageModel(),
    var sourceUrls: MutableList<String> = mutableListOf()
)

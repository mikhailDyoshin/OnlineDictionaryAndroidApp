package com.example.onlinedictionaryandroidappproject.data.storage.models


data class PhoneticsStorageModel(
    var audio: String? = null,
    var sourceUrl: String? = null,
    var license: LicenseStorageModel? = LicenseStorageModel()
)

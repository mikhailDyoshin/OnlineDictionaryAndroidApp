package com.example.onlinedictionaryandroidappproject.domain.usecase


import java.util.regex.Pattern
import javax.inject.Inject

class GetCountryUseCase @Inject constructor() {
    fun execute(audioURL: String?): String {

        if (audioURL != null) {

            val startToken = "-"

            val endToken = ".mp3"

            val startIndex = audioURL.lastIndexOf(startToken)

            val endIndex = audioURL.indexOf(endToken)

            if (startIndex != -1 && endIndex != -1) {
                return audioURL.substring(startIndex + startToken.length, endIndex).uppercase()
            }
        }

        return ""
    }
}
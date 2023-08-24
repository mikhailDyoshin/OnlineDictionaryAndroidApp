package com.example.onlinedictionaryandroidappproject.data.repository


import com.example.onlinedictionaryandroidappproject.common.Resource
import com.example.onlinedictionaryandroidappproject.data.storage.DictionaryApi
import com.example.onlinedictionaryandroidappproject.data.storage.models.MeaningsStorageModel
import com.example.onlinedictionaryandroidappproject.data.storage.models.WordStorageModel
import com.example.onlinedictionaryandroidappproject.domain.models.DefinitionsDomainModel
import com.example.onlinedictionaryandroidappproject.domain.models.MeaningsDomainModel
import com.example.onlinedictionaryandroidappproject.domain.models.WordDomainModel
import com.example.onlinedictionaryandroidappproject.domain.repository.DictionaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class DictionaryRepositoryImpl @Inject constructor(private val api: DictionaryApi) :
    DictionaryRepository {
    override fun getWord(word: String): Flow<Resource<List<WordDomainModel>>> = flow {
        try {
            emit(value = Resource.loading())

            val response = api.getWord(word = word)

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    emit(Resource.success(data = transformResponse(responseBody = body)))
                }
            } else {
                when (response.code()) {
                    404 -> emit(error(message = "Can't find anything, mate. Sorry:("))
                    500 -> emit(error(message = "Server error"))
                    else -> emit(error(message = "Undefined error!"))
                }
            }
        } catch (e: IOException) {
            emit(error(message = "Check internet connection."))
        }
    }

    private fun <T> error(message: String): Resource<T> {
        return Resource.error(message = message)
    }

    private fun transformResponse(responseBody: List<WordStorageModel>): List<WordDomainModel> {
        val transformedWordsList = mutableListOf<WordDomainModel>()

        for (w in responseBody) {
            transformedWordsList.add(
                transformModels(w)
            )
        }

        return transformedWordsList.toList()
    }

    private fun transformModels(source: WordStorageModel): WordDomainModel {
        return WordDomainModel(
            word = source.word,
            meanings = getMeanings(source)
        )
    }

    private fun getMeanings(word: WordStorageModel): MutableList<MeaningsDomainModel> {
        val meanings = word.meanings

        val transformedMeaningsList = mutableListOf<MeaningsDomainModel>()

        for (m in meanings) {
            transformedMeaningsList.add(
                MeaningsDomainModel(
                    partOfSpeech = m.partOfSpeech,
                    definitions = getDefinitions(m),
                    synonyms = m.synonyms,
                    antonyms = m.antonyms
                )
            )
        }

        return transformedMeaningsList
    }

    private fun getDefinitions(meaning: MeaningsStorageModel): MutableList<DefinitionsDomainModel> {
        val definitions = meaning.definitions

        val transformedDefinitionsList = mutableListOf<DefinitionsDomainModel>()

        for (d in definitions) {
            transformedDefinitionsList.add(
                DefinitionsDomainModel(
                    definition = d.definition,
                    synonyms = d.synonyms,
                    antonyms = d.antonyms,
                    example = d.example,
                )
            )
        }

        return transformedDefinitionsList
    }

}
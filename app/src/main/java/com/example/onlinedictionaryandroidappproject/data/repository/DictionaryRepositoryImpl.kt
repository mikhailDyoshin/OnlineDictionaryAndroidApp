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

        return responseBody.map {
            transformModels(it)
        }
    }

    private fun transformModels(source: WordStorageModel): WordDomainModel {
        return WordDomainModel(
            word = source.word,
            meanings = getMeanings(source)
        )
    }

    private fun getMeanings(word: WordStorageModel): List<MeaningsDomainModel> {
        val meanings = word.meanings

        return meanings.map {
            MeaningsDomainModel(
                partOfSpeech = it.partOfSpeech,
                definitions = getDefinitions(it),
                synonyms = it.synonyms,
                antonyms = it.antonyms
            )
        }
    }

    private fun getDefinitions(meaning: MeaningsStorageModel): List<DefinitionsDomainModel> {
        val definitions = meaning.definitions

        return definitions.map {
            DefinitionsDomainModel(
                definition = it.definition,
                synonyms = it.synonyms,
                antonyms = it.antonyms,
                example = it.example,
            )
        }
    }

}

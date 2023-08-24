package com.example.onlinedictionaryandroidappproject.presentation

import android.app.DownloadManager.Request
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlinedictionaryandroidappproject.common.Resource
import com.example.onlinedictionaryandroidappproject.domain.models.MeaningsDomainModel
import com.example.onlinedictionaryandroidappproject.domain.models.WordDomainModel
import com.example.onlinedictionaryandroidappproject.domain.usecase.GetWordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor(private val getWordUseCase: GetWordUseCase) : ViewModel() {

    private val _wordState: MutableLiveData<Resource<RequestState>> = MutableLiveData(
        Resource.success(data = RequestState())
    )

    val wordState: LiveData<Resource<RequestState>>
        get() = _wordState

//    init {
//        getWord("hello")
//    }

    fun getWord(word: String) {

        getWordUseCase.execute(word).onEach { result ->
            when (result.status) {
                Resource.Status.SUCCESS -> {
                    _wordState.value = Resource.success(
                        data = RequestState(
                            statusMessage = "",
                            wordsStatesList = transformResponse(result.data!!)
                        )
                    )
                }

                Resource.Status.ERROR -> {
                    _wordState.value = Resource.error(
                        message = result.message!!,
                        data = RequestState(statusMessage = "", wordsStatesList = listOf())
                    )
                }

                Resource.Status.LOADING -> {
                    _wordState.value = Resource.loading(
                        data = RequestState(
                            statusMessage = "Loading...",
                            wordsStatesList = listOf()
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)


    }

    private fun transformResponse(responseBody: List<WordDomainModel>): List<WordState> {
        val transformedWordsList = mutableListOf<WordState>()

        for (w in responseBody) {
            transformedWordsList.add(
                transformModels(w)
            )
        }

        return transformedWordsList.toList()
    }

    private fun transformModels(source: WordDomainModel): WordState {
        return WordState(
            word = source.word,
            meanings = getMeanings(source)
        )
    }

    private fun getMeanings(word: WordDomainModel): MutableList<MeaningsState> {
        val meanings = word.meanings

        val transformedMeaningsList = mutableListOf<MeaningsState>()

        for (m in meanings) {
            transformedMeaningsList.add(
                MeaningsState(
                    partOfSpeech = m.partOfSpeech,
                    definitions = getDefinitions(m),
                    synonyms = m.synonyms,
                    antonyms = m.antonyms
                )
            )
        }

        return transformedMeaningsList
    }

    private fun getDefinitions(meaning: MeaningsDomainModel): MutableList<DefinitionsState> {
        val definitions = meaning.definitions

        val transformedDefinitionsList = mutableListOf<DefinitionsState>()

        for (d in definitions) {
            transformedDefinitionsList.add(
                DefinitionsState(
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
package com.example.onlinedictionaryandroidappproject.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlinedictionaryandroidappproject.common.Resource
import com.example.onlinedictionaryandroidappproject.domain.models.MeaningsDomainModel
import com.example.onlinedictionaryandroidappproject.domain.models.WordDomainModel
import com.example.onlinedictionaryandroidappproject.domain.usecase.GetWordUseCase
import com.example.onlinedictionaryandroidappproject.presentation.state.DefinitionsState
import com.example.onlinedictionaryandroidappproject.presentation.state.MeaningsState
import com.example.onlinedictionaryandroidappproject.presentation.state.RequestState
import com.example.onlinedictionaryandroidappproject.presentation.state.WordState
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

    fun getWord(word: String) {

        getWordUseCase.execute(word).onEach { result ->
            when (result.status) {
                Resource.Status.SUCCESS -> {
                    if (result.data == null) {
                        _wordState.value = Resource.error(
                            message = "No data!",
                            data = RequestState(statusMessage = "", wordsStatesList = listOf())
                        )
                    } else {
                        _wordState.value = Resource.success(
                            data = RequestState(
                                statusMessage = "",
                                wordsStatesList = transformResponse(result.data)
                            )
                        )
                    }

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

        return responseBody.map { transformModels(source = it) }
    }

    private fun transformModels(source: WordDomainModel): WordState {
        return WordState(
            word = source.word,
            meanings = transformMeanings(source)
        )
    }

    private fun transformMeanings(word: WordDomainModel): List<MeaningsState> {
        val meanings = word.meanings

        return meanings.map {
            MeaningsState(
                partOfSpeech = it.partOfSpeech,
                definitions = transformDefinitions(it),
                synonyms = it.synonyms,
                antonyms = it.antonyms
            )
        }
    }

    private fun transformDefinitions(meaning: MeaningsDomainModel): List<DefinitionsState> {
        val definitions = meaning.definitions

        return definitions.map {
            DefinitionsState(
                definition = it.definition,
                synonyms = it.synonyms,
                antonyms = it.antonyms,
                example = it.example,
            )
        }
    }

}
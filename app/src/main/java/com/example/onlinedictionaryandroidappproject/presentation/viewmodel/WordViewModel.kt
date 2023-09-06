package com.example.onlinedictionaryandroidappproject.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onlinedictionaryandroidappproject.common.Resource
import com.example.onlinedictionaryandroidappproject.domain.models.MeaningsDomainModel
import com.example.onlinedictionaryandroidappproject.domain.models.WordDomainModel
import com.example.onlinedictionaryandroidappproject.domain.usecase.GetWordUseCase
import com.example.onlinedictionaryandroidappproject.presentation.nav_arg_data.DefinitionDetailNavData
import com.example.onlinedictionaryandroidappproject.presentation.nav_arg_data.MeaningDetailNavData
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

    fun getWords(dataState: Resource<RequestState>): List<String> {

        val words = dataState.data?.wordsStatesList ?: listOf()

        if (words != listOf<WordState>()) {

            return words.map { it.word ?: "" }
        }

        return listOf()
    }

    fun getMeanings(dataState: Resource<RequestState>, id: Int): List<String> {

        val words = dataState.data?.wordsStatesList ?: listOf()

        if (words != mutableListOf<WordState>()) {

            val word = words[id]

            return word.meanings.map { it.partOfSpeech ?: "" }
        }

        return listOf()
    }

    fun getDefinitions(
        dataState: Resource<RequestState>,
        navData: MeaningDetailNavData
    ): List<String> {

        val wordID = navData.wordID
        val meaningID = navData.meaningID

        val words = dataState.data?.wordsStatesList ?: listOf()

        if (words != listOf<WordState>()) {

            val meaning = words[wordID].meanings[meaningID]

            return meaning.definitions.map { it.definition ?: "" }
        }

        return listOf()
    }

    fun getDefinitionDetail(
        dataState: Resource<RequestState>, navData: DefinitionDetailNavData
    ): DefinitionsState {

        val wordID = navData.wordID
        val meaningID = navData.meaningID
        val definitionID = navData.definitionID

        val words = dataState.data?.wordsStatesList ?: listOf()

        if (words != mutableListOf<WordState>()) {

            return words[wordID].meanings[meaningID].definitions[definitionID]
        }

        return DefinitionsState()
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
            meanings = transformMeanings(source)
        )
    }

    private fun transformMeanings(word: WordDomainModel): List<MeaningsState> {
        val meanings = word.meanings

        val transformedMeaningsList = mutableListOf<MeaningsState>()

        for (m in meanings) {
            transformedMeaningsList.add(
                MeaningsState(
                    partOfSpeech = m.partOfSpeech,
                    definitions = transformDefinitions(m),
                    synonyms = m.synonyms,
                    antonyms = m.antonyms
                )
            )
        }

        return transformedMeaningsList
    }

    private fun transformDefinitions(meaning: MeaningsDomainModel): List<DefinitionsState> {
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
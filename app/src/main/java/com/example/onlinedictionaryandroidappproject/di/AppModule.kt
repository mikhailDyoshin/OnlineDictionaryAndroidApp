package com.example.onlinedictionaryandroidappproject.di

import com.example.onlinedictionaryandroidappproject.common.Constants
import com.example.onlinedictionaryandroidappproject.data.repository.DictionaryRepositoryImpl
import com.example.onlinedictionaryandroidappproject.data.storage.DictionaryApi
import com.example.onlinedictionaryandroidappproject.domain.repository.DictionaryRepository
import com.example.onlinedictionaryandroidappproject.domain.usecase.GetCountryUseCase
import com.example.onlinedictionaryandroidappproject.domain.usecase.GetWordUseCase
import com.example.onlinedictionaryandroidappproject.presentation.viewmodel.WordViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDictionaryApi(): DictionaryApi {

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(DictionaryApi::class.java)
    }


    @Provides
    @Singleton
    fun provideDictionaryRepository(api: DictionaryApi): DictionaryRepository {
        return DictionaryRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideWordViewModel(
        getWordUseCase: GetWordUseCase,
        getCountryUseCase: GetCountryUseCase,
    ): WordViewModel {
        return WordViewModel(getWordUseCase, getCountryUseCase)
    }

}

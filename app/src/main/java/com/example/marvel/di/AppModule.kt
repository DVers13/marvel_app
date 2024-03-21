package com.example.marvel.di

import com.example.marvel.data.data_source.MarvelApi
import com.example.marvel.data.repository.MarvelRepositoryImpl
import com.example.marvel.domain.repository.MarvelRepository
import com.example.marvel.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
@Provides
@Singleton
fun provideMarvelApi(): MarvelApi {
    return Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MarvelApi::class.java)
}
    @Provides
    @Singleton
    fun provideMarvelRepository(api: MarvelApi):MarvelRepository{
        return MarvelRepositoryImpl(api)
    }

}
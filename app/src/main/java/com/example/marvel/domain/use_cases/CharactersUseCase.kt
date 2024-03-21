package com.example.marvel.domain.use_cases

import com.example.marvel.domain.model.Character
import com.example.marvel.domain.repository.MarvelRepository
import com.example.marvel.util.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CharactersUseCase @Inject constructor(
    private val repository : MarvelRepository
){
    operator fun invoke(offset:Int): Flow<Response<List<Character>>> = flow{
        try{
            emit(Response.Loading<List<Character>>())
            val list = repository.getAllCharacter(offset=offset).data.results.map{
                it.toCharacter()
            }
            emit(Response.Success<List<Character>>(list))
        }
        catch (e:HttpException){
            emit(Response.Error<List<Character>>(e.printStackTrace().toString()))
        }
        catch (e:IOException){
            emit(Response.Error<List<Character>>(e.printStackTrace().toString()))
        }
    }
}
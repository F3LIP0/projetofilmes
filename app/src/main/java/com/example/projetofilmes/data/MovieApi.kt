package com.example.projetofilmes.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Constantes para a API
private const val API_KEY = "7b8883a1" // Sua chave da API
private const val BASE_URL = "https://www.omdbapi.com"

// Interface que define os endpoints da API
interface MovieApi {
    @GET("/")
    suspend fun searchMovies(
        @Query("s") query: String,
        @Query("apikey") apiKey: String = API_KEY
    ): MovieSearchResponse

    @GET("/")
    suspend fun getMovieById(
        @Query("i") imdbId: String,
        @Query("apikey") apiKey: String = API_KEY
    ): MovieDetail
}

// Objeto Singleton para criar uma única instância do Retrofit para todo o app
object RetrofitInstance {
    val api: MovieApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
    }
}

package com.example.projetofilmes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetofilmes.data.Movie
import com.example.projetofilmes.data.MovieDetail
import com.example.projetofilmes.data.RetrofitInstance
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {

    // LiveData para a lista de filmes
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    // LiveData para os detalhes de um filme
    private val _movieDetail = MutableLiveData<MovieDetail?>()
    val movieDetail: LiveData<MovieDetail?> = _movieDetail

    // LiveData para o estado de carregamento
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    // LiveData para mensagens de erro
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    init {
        searchMovies("Batman")
    }

    fun searchMovies(query: String) {
        if (query.isBlank()) {
            _movies.value = emptyList()
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = RetrofitInstance.api.searchMovies(query = query)
                if (response.response == "True" && response.search != null) {
                    _movies.value = response.search!!
                } else {
                    _movies.value = emptyList()
                    _errorMessage.value = response.error ?: "Nenhum filme encontrado."
                }
            } catch (e: Exception) {
                _movies.value = emptyList()
                _errorMessage.value = "Falha na conexão. Verifique sua internet."
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchMovieById(imdbId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            _movieDetail.value = null
            try {
                val detail = RetrofitInstance.api.getMovieById(imdbId)
                if (detail.response == "True") {
                    _movieDetail.value = detail
                } else {
                    _errorMessage.value = "Filme não encontrado."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Falha ao buscar detalhes. Verifique sua conexão."
            } finally {
                _isLoading.value = false
            }
        }
    }
}

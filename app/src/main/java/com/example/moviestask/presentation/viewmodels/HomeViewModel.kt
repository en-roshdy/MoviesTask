package com.example.moviestask.presentation.viewmodels

import MovieModel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviestask.domain.usecase.MoviesRepositoryImpl
import com.example.moviestask.utils.BaseResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val moviesRepositoryImpl: MoviesRepositoryImpl) :
    ViewModel() {

    /// Popular Movies
    private val nowPlayingMoviesMutable = MutableLiveData<BaseResponse<List<MovieModel>>>()
    val nowPlayingMoviesResponse: LiveData<BaseResponse<List<MovieModel>>> get() = nowPlayingMoviesMutable

    /// Popular Movies
    private val upComingMoviesMutable = MutableLiveData<BaseResponse<List<MovieModel>>>()
    val upComingMoviesResponse: LiveData<BaseResponse<List<MovieModel>>> get() = upComingMoviesMutable

    /// Popular Movies
    private val popularMoviesMutable = MutableLiveData<BaseResponse<List<MovieModel>>>()
    val popularMoviesResponse: LiveData<BaseResponse<List<MovieModel>>> get() = popularMoviesMutable


    fun getNowPlayingMovies() {
        viewModelScope.launch {
            val response = BaseResponse<List<MovieModel>>()


            try {
                val nowPlayingMovies = moviesRepositoryImpl.getNowPlaying()
                response.data = nowPlayingMovies.results

            } catch (e: Exception) {
                Log.d("getNowPlayingMovies", "getNowPlayingMovies: ")
                response.throwable = e
                response.errorString = response.errorToString(e)



            }
            withContext(Dispatchers.Main) {
                nowPlayingMoviesMutable.value = response
            }
        }
    }

    fun getUpComingMovies() {
        viewModelScope.launch {
            val response = BaseResponse<List<MovieModel>>()

            try {
                val upComingMovies = moviesRepositoryImpl.getUpcomingMovies()
                response.data = upComingMovies.results
            } catch (e: Exception) {
                response.throwable = e
                response.errorString = response.errorToString(e)


            }

            withContext(Dispatchers.Main) {
                upComingMoviesMutable.value = response
            }

        }
    }

    fun getPopularMovies() {
        viewModelScope.launch {
            val response = BaseResponse<List<MovieModel>>()


            try {
                val popularMovies = moviesRepositoryImpl.getPopularMovies()
                response.data = popularMovies.results
            } catch (e: Exception) {
                Log.e("getPopularError", "getPopularMovies: $e", )
                response.throwable = e
                response.errorString = response.errorToString(e)


            }
            withContext(Dispatchers.Main) {
                popularMoviesMutable.value = response
            }
        }
    }

}
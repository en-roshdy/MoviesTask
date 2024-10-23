package com.example.moviestask.data.repositories

import com.example.moviestask.domain.models.MovieDetailsResponse
import com.example.moviestask.domain.models.MoviesResponse


interface MoviesRepo {

    suspend fun getNowPlaying(): MoviesResponse

    suspend fun getPopularMovies(): MoviesResponse
    suspend fun getUpcomingMovies(): MoviesResponse
    suspend fun getMovieDetails(movieId: Int): MovieDetailsResponse
}
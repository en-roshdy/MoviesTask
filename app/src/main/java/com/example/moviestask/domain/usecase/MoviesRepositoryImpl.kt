package com.example.moviestask.domain.usecase

import com.example.moviestask.data.data_source.MoviesApiService
import com.example.moviestask.data.repositories.MoviesRepo
import com.example.moviestask.domain.models.MovieDetailsResponse
import com.example.moviestask.domain.models.MoviesResponse
import javax.inject.Inject


class MoviesRepositoryImpl @Inject constructor(private val apiService: MoviesApiService) :
    MoviesRepo {


    override suspend fun getNowPlaying(): MoviesResponse {
        return apiService.gerNowPlaying()

    }

    override suspend fun getPopularMovies(): MoviesResponse {
        return apiService.getPopularMovies()
    }

    override suspend fun getUpcomingMovies(): MoviesResponse {
        return apiService.getUpComingMovies()
    }

    override suspend fun getMovieDetails(movieId: Int): MovieDetailsResponse {
        return  apiService.getMovieDetails(movieId)
    }
}
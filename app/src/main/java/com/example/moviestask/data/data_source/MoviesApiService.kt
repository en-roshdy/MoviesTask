package com.example.moviestask.data.data_source

import com.example.moviestask.domain.models.MoviesResponse
import retrofit2.http.GET

interface MoviesApiService {

    @GET("movie/now_playing")
    suspend fun gerNowPlaying (): MoviesResponse


    @GET("movie/popular")
    suspend fun getPopularMovies (): MoviesResponse


    @GET("movie/upcoming")
    suspend fun getUpComingMovies (): MoviesResponse



}
package com.example.moviestask.data.data_source

import com.example.moviestask.domain.models.MovieDetailsResponse
import com.example.moviestask.domain.models.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesApiService {

    @GET("movie/now_playing")
    suspend fun gerNowPlaying (): MoviesResponse


    @GET("movie/popular")
    suspend fun getPopularMovies (): MoviesResponse


    @GET("movie/upcoming")
    suspend fun getUpComingMovies (): MoviesResponse



    @GET("movie/{movie_id}")
    suspend fun getMovieDetails (@Path("movie_id") movieId : Int): MovieDetailsResponse



}
package com.example.moviestask.data.repositories


interface MoviesRepo {

    suspend fun getNowPlaying() : MoviesResponse

    suspend fun getPopularMovies(): MoviesResponse
    suspend fun getUpcomingMovies(): MoviesResponse
//    suspend fun getMovieDetails(movieId: Int): MovieDetails
}
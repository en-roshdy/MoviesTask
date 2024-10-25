package com.example.moviestask.domain.models

data class Genre(
    val id: Int,
    val name: String
){
    override fun toString(): String {
        return name
    }
}
package com.example.moviestask.presentation.ui.adapters

import MovieModel
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviestask.R
import com.example.moviestask.databinding.ItemMovieBinding
import com.example.moviestask.utils.Constants

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MoviesHolder>() {

    private val moviesList: ArrayList<MovieModel> = ArrayList()

    fun insertMovies(movies: List<MovieModel>) {
        moviesList.addAll(movies)
        notifyItemRangeInserted(itemCount, moviesList.size)
    }

    class MoviesHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemMovieBinding = ItemMovieBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
        return MoviesHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MoviesHolder, position: Int) {
        val movieModel = moviesList[position]
        val binding = holder.binding

        Glide.with(holder.itemView.context).load(Constants.IMAGES_URL + movieModel.poster_path).into(binding.movieImage)

        binding.tvMovieTitle.text = movieModel.title

        binding.tvReleaseDate.text = "Released at ${movieModel.release_date}"
    }
}
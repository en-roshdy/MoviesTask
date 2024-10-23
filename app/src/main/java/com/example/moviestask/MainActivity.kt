package com.example.moviestask

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviestask.databinding.ActivityMainBinding
import com.example.moviestask.presentation.ui.adapters.MoviesAdapter
import com.example.moviestask.presentation.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    private lateinit var popularAdapter: MoviesAdapter

    private lateinit var nowPlayingAdapter: MoviesAdapter

    private lateinit var upComingAdapter: MoviesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        handleRecyclerViews()

        observePopular()
        observeNowPlaying()
        observeUpComing()

        homeViewModel.getPopularMovies()
        homeViewModel.getNowPlayingMovies()
        homeViewModel.getUpComingMovies()


    }


    private fun handleRecyclerViews() {
        nowPlayingAdapter = MoviesAdapter()
        binding.rvNowPlaying.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvNowPlaying.adapter = nowPlayingAdapter


        upComingAdapter = MoviesAdapter()
        binding.rvUpComingMovies.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvUpComingMovies.adapter = upComingAdapter

        popularAdapter = MoviesAdapter()
        binding.rvPopularMovies.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPopularMovies.adapter = popularAdapter
    }


    private fun observePopular() {
        homeViewModel.popularMoviesResponse.observe(this) {


            if (it.throwable != null || it.data == null) {
                Log.e(TAG, "observePopular: Error ${it.throwable}",)
                return@observe
            }
            popularAdapter.insertMovies(it.data!!)

            Log.d(TAG, "observePopular: ${it.data!![0].title}")
        }
    }

    private fun observeNowPlaying() {
        homeViewModel.nowPlayingMoviesResponse.observe(this) {
            if (it.throwable != null || it.data == null) {
                return@observe
            }
            nowPlayingAdapter.insertMovies(it.data!!)
        }
    }

    private fun observeUpComing() {
        homeViewModel.upComingMoviesResponse.observe(this) {
            if (it.throwable != null || it.data == null) {
                return@observe
            }

            upComingAdapter.insertMovies(it.data!!)
        }
    }
}
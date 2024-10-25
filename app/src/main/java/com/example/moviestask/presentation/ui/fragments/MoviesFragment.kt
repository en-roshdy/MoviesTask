package com.example.moviestask.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviestask.R
import com.example.moviestask.databinding.FragmentMoviesBinding
import com.example.moviestask.presentation.ui.adapters.MoviesAdapter
import com.example.moviestask.presentation.viewmodels.HomeViewModel
import com.example.moviestask.utils.Constants.MOVIE_ID


class MoviesFragment : Fragment(), MoviesAdapter.MovieClickListener {


    private lateinit var binding: FragmentMoviesBinding


    private lateinit var popularAdapter: MoviesAdapter

    private lateinit var nowPlayingAdapter: MoviesAdapter

    private lateinit var upComingAdapter: MoviesAdapter

    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(inflater)
        handleRecyclerViews()

        observePopular()
        observeNowPlaying()
        observeUpComing()

        handleTryAgain()

        homeViewModel.getMovies();

        return binding.root

    }


    private fun handleRecyclerViews() {
        nowPlayingAdapter = MoviesAdapter(this)
        binding.rvNowPlaying.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvNowPlaying.adapter = nowPlayingAdapter


        upComingAdapter = MoviesAdapter(this)
        binding.rvUpComingMovies.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvUpComingMovies.adapter = upComingAdapter

        popularAdapter = MoviesAdapter(this)
        binding.rvPopularMovies.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvPopularMovies.adapter = popularAdapter
    }


    private fun observePopular() {
        homeViewModel.popularMoviesResponse.observe(viewLifecycleOwner) {

            binding.popularLoading.loadingView.visibility = View.GONE

            if (it.throwable != null || it.data == null) {
                binding.errorPopular.errorLayout.visibility = View.VISIBLE
                binding.errorPopular.textView.append(it.throwable.toString())
                return@observe
            }
            popularAdapter.insertMovies(it.data!!)


        }
    }

    private fun observeNowPlaying() {

        homeViewModel.nowPlayingMoviesResponse.observe(viewLifecycleOwner) {
            binding.noPlayingLoading.loadingView.visibility = View.GONE
            if (it.throwable != null || it.data == null) {
                binding.errorNoPlaying.errorLayout.visibility = View.VISIBLE
                binding.errorNoPlaying.textView.append(it.throwable.toString())

                return@observe
            }
            nowPlayingAdapter.insertMovies(it.data!!)
        }
    }

    private fun observeUpComing() {
        homeViewModel.upComingMoviesResponse.observe(viewLifecycleOwner) {

            binding.upComingLoading.loadingView.visibility = View.GONE
            if (it.throwable != null || it.data == null) {
                Log.e(TAG, "observeUpComing: Error ${it.throwable}")
                binding.errorUpComing.errorLayout.visibility = View.VISIBLE
                binding.errorUpComing.textView.append(it.throwable.toString())

                return@observe
            }

            upComingAdapter.insertMovies(it.data!!)
        }
    }

    private val TAG = "MoviesFragment"
    override fun onMovieClicked(movieId: Int) {
        Log.d(TAG, "onMovieClicked: Movie Id = $movieId")
        val bundle = Bundle()
        bundle.putInt(MOVIE_ID, movieId)
        findNavController().navigate(R.id.action_moviesFragment_to_movieDetailsFragment, bundle)
    }


    private fun handleTryAgain() {
        binding.errorPopular.btnTryAgain.setOnClickListener {
            binding.errorPopular.errorLayout.visibility = View.GONE
            binding.popularLoading.loadingView.visibility = View.VISIBLE
            homeViewModel.getPopularMovies()
        }

        binding.errorUpComing.btnTryAgain.setOnClickListener {
            binding.errorUpComing.errorLayout.visibility = View.GONE
            binding.upComingLoading.loadingView.visibility = View.VISIBLE
            homeViewModel.getUpComingMovies()
        }

        binding.errorNoPlaying.btnTryAgain.setOnClickListener {
            binding.errorNoPlaying.errorLayout.visibility = View.GONE
            binding.noPlayingLoading.loadingView.visibility = View.VISIBLE
            homeViewModel.getNowPlayingMovies()
        }

    }
}
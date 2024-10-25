package com.example.moviestask.presentation.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviestask.R
import com.example.moviestask.databinding.FragmentMoviesBinding
import com.example.moviestask.presentation.ui.adapters.MoviesAdapter
import com.example.moviestask.presentation.viewmodels.HomeViewModel
import com.example.moviestask.utils.Constants.MOVIE_ID
import com.example.moviestask.utils.hide
import com.example.moviestask.utils.show


class MoviesFragment : Fragment(), MoviesAdapter.MovieClickListener {


    private lateinit var binding: FragmentMoviesBinding


    private lateinit var popularAdapter: MoviesAdapter

    private lateinit var nowPlayingAdapter: MoviesAdapter

    private lateinit var upComingAdapter: MoviesAdapter

    private val connectionError = "Check Internet Connection"
    private val errorOccurred = "An Error Occurred"

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


        getMovies();
        return binding.root

    }


    private fun getMovies(){
        if(isInternetAvailable()){
            binding.popularLoading.loadingView.show()

            binding.noPlayingLoading.loadingView.show()

            binding.upComingLoading.loadingView.show()

            homeViewModel.getMovies();

        }else{

            binding.popularLoading.loadingView.hide()
            binding.errorPopular.errorLayout.show()
            binding.errorPopular.textView.text = connectionError

            binding.noPlayingLoading.loadingView.hide()

            binding.errorNoPlaying.errorLayout.show()
            binding.errorNoPlaying.textView.text = connectionError

            binding.upComingLoading.loadingView.hide()
            binding.errorUpComing.errorLayout.show()
            binding.errorUpComing.textView.text = connectionError


        }
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


    @SuppressLint("SetTextI18n")
    private fun observePopular() {
        homeViewModel.popularMoviesResponse.observe(viewLifecycleOwner) {

            binding.popularLoading.loadingView.hide()

            if (it.throwable != null || it.data == null) {
                binding.errorPopular.errorLayout.show()
                binding.errorPopular.textView.text = "$errorOccurred\n ${it.throwable}"
                return@observe
            }
            popularAdapter.insertMovies(it.data!!)


        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeNowPlaying() {

        homeViewModel.nowPlayingMoviesResponse.observe(viewLifecycleOwner) {
            binding.noPlayingLoading.loadingView.hide()
            if (it.throwable != null || it.data == null) {
                binding.errorNoPlaying.errorLayout.show()

                binding.errorNoPlaying.textView.text = "$errorOccurred\n ${it.throwable}"

                return@observe
            }
            nowPlayingAdapter.insertMovies(it.data!!)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun observeUpComing() {
        homeViewModel.upComingMoviesResponse.observe(viewLifecycleOwner) {

            binding.upComingLoading.loadingView.hide()
            if (it.throwable != null || it.data == null) {
                Log.e(TAG, "observeUpComing: Error ${it.throwable}")
                binding.errorUpComing.errorLayout.show()
             binding.errorUpComing.textView.text = "$errorOccurred\n ${it.throwable}"

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
            binding.errorPopular.errorLayout.hide()
            binding.popularLoading.loadingView.show()
            getMovies()
        }

        binding.errorUpComing.btnTryAgain.setOnClickListener {
            binding.errorUpComing.errorLayout.hide()
            binding.upComingLoading.loadingView.show()
            getMovies()
        }

        binding.errorNoPlaying.btnTryAgain.setOnClickListener {
            binding.errorNoPlaying.errorLayout.hide()
            binding.noPlayingLoading.loadingView.show()
            getMovies()
        }

    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }
}
package com.example.moviestask.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.example.moviestask.R
import com.example.moviestask.databinding.FragmentMovieDetailsBinding
import com.example.moviestask.databinding.FragmentMoviesBinding
import com.example.moviestask.domain.models.MovieDetailsResponse
import com.example.moviestask.presentation.viewmodels.HomeViewModel
import com.example.moviestask.utils.Constants

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MovieDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backClick.setOnClickListener {
            homeViewModel.clearMovieData()
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        val movieId = arguments?.getInt("movieId", 0) ?: 0
        observeMovieDetails()
        if (homeViewModel.movieDetailsResponse.value?.data == null) {
            binding.loadingView.visibility = View.VISIBLE
        }
        homeViewModel.getMovieDetails(movieId)
    }

    private fun observeMovieDetails() {
        homeViewModel.movieDetailsResponse.observe(viewLifecycleOwner) {

            binding.loadingView.visibility = View.GONE
            if (it.throwable != null || it.data == null) {
                return@observe
            }
            setData(it.data!!)
            Log.d("observeMovieDetails", "observeMovieDetails: ${it.data?.adult}")
        }
    }

    private fun setData(movieData: MovieDetailsResponse) {
        binding.tvMovieName.text = movieData.title
        binding.tvOverView.text = movieData.overview
        binding.tvVote.text = "${movieData.vote_count}"
        binding.tvReleaseDate.text = movieData.release_date

        val genreNames = movieData.genres.joinToString(separator = "-")
        binding.tvGenre.text = genreNames




        Glide.with(requireContext()).load(Constants.IMAGES_URL + movieData.poster_path)
            .into(binding.posterImage)
    }
}
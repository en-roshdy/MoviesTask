package com.example.moviestask.presentation.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.moviestask.R
import com.example.moviestask.databinding.FragmentMovieDetailsBinding
import com.example.moviestask.databinding.FragmentMoviesBinding
import com.example.moviestask.presentation.viewmodels.HomeViewModel

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
    private val homeViewModel : HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movieId = arguments?.getInt("movieId", 0) ?: 0
        observeMovieDetails()
        homeViewModel.getMovieDetails(movieId)
    }

    private fun observeMovieDetails(){
        homeViewModel.movieDetailsResponse.observe(viewLifecycleOwner){

            if(it.throwable != null || it.data == null){
                return@observe
            }
            Log.d("observeMovieDetails", "observeMovieDetails: ${it.data?.adult}")

        }
    }
}
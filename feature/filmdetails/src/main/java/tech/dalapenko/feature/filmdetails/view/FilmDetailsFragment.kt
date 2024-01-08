package tech.dalapenko.feature.filmdetails.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tech.dalapenko.core.basepresentation.utils.ImageTransform
import tech.dalapenko.core.basepresentation.utils.loadImage
import tech.dalapenko.data.filmdetails.model.Film
import tech.dalapenko.feature.filmdetails.R
import tech.dalapenko.feature.filmdetails.databinding.FilmDetailsBinding
import tech.dalapenko.feature.filmdetails.viewmodel.FilmDetailsViewModel
import tech.dalapenko.feature.filmdetails.viewmodel.UiState

@AndroidEntryPoint
class FilmDetailsFragment : Fragment(R.layout.film_details) {

    private var _binding: FilmDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FilmDetailsViewModel by viewModels()
    private val args: FilmDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FilmDetailsBinding.inflate(inflater, container, false)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.contentStateFlow.collect { state ->
                    when (state) {
                        is UiState.Success -> {
                            bindingFilmDetailsContent(state.data)
                        }
                        is UiState.Loading -> {
                            bindingLoader()
                        }
                        is UiState.Error -> {
                            bindingError()
                        }
                    }
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchFilmData(args.filmId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindingFilmDetailsContent(film: Film) {
        with(binding) {
            (film.coverUrl ?: film.posterUrl)?.let { cover.loadImage(it, ImageTransform.CropTop()) }
            film.logoUrl?.let { logo.loadImage(it, ImageTransform.FitCenter()) }
            film.ruName?.let(title::setText)
            film.description?.let(description::setText)

            error.isVisible = false
            loader.isVisible = false
        }
    }

    private fun bindingError() = with(binding) {
        error.isVisible = true
        loader.isVisible = false
    }

    private fun bindingLoader() = with(binding) {
        error.isVisible = false
        loader.isVisible = true
    }
}
package tech.dalapenko.feature.premieres.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tech.dalapenko.core.basepresentation.navigate.Animation
import tech.dalapenko.core.basepresentation.navigate.Deeplink
import tech.dalapenko.premieres.R
import tech.dalapenko.premieres.databinding.PremieresBinding
import tech.dalapenko.feature.premieres.viewmodel.PremieresViewModel
import tech.dalapenko.feature.premieres.viewmodel.UiState
import java.time.LocalDate

@AndroidEntryPoint
class PremieresFragment : Fragment(R.layout.premieres) {

    private var _binding: PremieresBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PremieresViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PremieresBinding.inflate(inflater, container, false)
        binding.content.layoutManager = LinearLayoutManager(context)
        with(binding.refresh) {
            setOnRefreshListener {
                fetchCurrentData()
                isRefreshing = false
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.contentStateFlow.collect { state ->
                    when (state) {
                        is UiState.CurrentDataReady -> {
                            binding.cachedState.isVisible = false
                            binding.loader.isVisible = false
                            binding.error.isVisible = false
                            binding.content.isVisible = true
                            binding.content.adapter = PremieresRecyclerAdapter(state.data) {
                                findNavController().navigate(
                                    request = Deeplink.openFilmDetails(it.premiere.id),
                                    navOptions = Animation.slideRight(R.id.premieres_root)
                                )
                            }
                        }
                        is UiState.CachedDataReady -> {
                            binding.cachedState.isVisible = true
                            binding.loader.isVisible = false
                            binding.error.isVisible = false
                            binding.content.isVisible = true
                            binding.content.adapter = PremieresRecyclerAdapter(state.data) {
                                Toast.makeText(
                                    context,
                                    "Can't open details with cached result",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        is UiState.Loading -> {
                            binding.loader.isVisible = true
                            binding.content.isVisible = false
                            binding.error.isVisible = false
                        }
                        is UiState.Error -> {
                            binding.loader.isVisible = false
                            binding.content.isVisible = false
                            binding.error.isVisible = true
                        }
                    }
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchCurrentData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fetchCurrentData() {
        val (month, year) = with(LocalDate.now()) {
            month.name to year
        }
        viewModel.fetchContentList(month, year)
    }
}
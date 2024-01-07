package tech.dalapenko.feature.releases.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tech.dalapenko.releases.R
import tech.dalapenko.releases.databinding.ReleasesBinding
import tech.dalapenko.feature.releases.viewmodel.UiState
import tech.dalapenko.feature.releases.viewmodel.ReleaseViewModel
import java.time.LocalDate

@AndroidEntryPoint
class ReleasesFragment : Fragment(R.layout.releases) {

    private var _binding: ReleasesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReleaseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ReleasesBinding.inflate(inflater, container, false)
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
                        is UiState.CurrentData -> {
                            binding.cachedState.isVisible = false
                            binding.loader.isVisible = false
                            binding.error.isVisible = false
                            binding.content.isVisible = true
                            binding.content.adapter = ReleaseRecyclerAdapter(state.data) {
                                val deeplink = NavDeepLinkRequest.Builder
                                    .fromUri("kinosearch://filmdetails/${it.id}".toUri())
                                    .build()
                                findNavController().navigate(deeplink)
                            }
                        }
                        is UiState.CachedData -> {
                            binding.cachedState.isVisible = true
                            binding.loader.isVisible = false
                            binding.error.isVisible = false
                            binding.content.isVisible = true
                            binding.content.adapter = ReleaseRecyclerAdapter(state.data) {
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
                        is UiState.Empty -> {
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
        viewModel.fetchReleasesList(month, year)
    }
}
package tech.dalapenko.feature.releases.view

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
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import tech.dalapenko.core.basepresentation.navigate.Animation
import tech.dalapenko.core.basepresentation.navigate.Deeplink
import tech.dalapenko.feature.releases.R
import tech.dalapenko.feature.releases.databinding.ReleasesBinding
import tech.dalapenko.feature.releases.model.UiState
import tech.dalapenko.feature.releases.viewmodel.ReleaseViewModel
import java.time.LocalDate

@AndroidEntryPoint
class ReleasesFragment : Fragment(R.layout.releases) {

    private var _binding: ReleasesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ReleaseViewModel by viewModels()

    private val releasesRecyclerAdapter = ReleasePagingAdapter {
        findNavController().navigate(
            request = Deeplink.openFilmDetails(it.release.id),
            navOptions = Animation.slideRight(R.id.releases_root)
        )
    }

    private val loadStateFooterAdapter by lazy {
        ReleasePagingLoadStateAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ReleasesBinding.inflate(inflater, container, false)
        with(binding) {
            refreshViewBinding(refresh)
            contentViewBinding(content)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiStateFlow.collectLatest(::onUiStateChange)
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

    private suspend fun onUiStateChange(uiState: UiState) = with(binding) {
        content.isVisible = uiState.isNetworkAvailable
        cachedState.isVisible = !uiState.isNetworkAvailable

        if (!uiState.isNetworkAvailable) return@with

        uiState.pagingDataFlow.collect(releasesRecyclerAdapter::submitData)
    }

    private fun contentViewBinding(view: RecyclerView) = with(view) {
        layoutManager = LinearLayoutManager(context)

        adapter = releasesRecyclerAdapter.apply {
            addLoadStateListener { loadState ->
                val isInitializing = loadState.refresh is LoadState.Loading && itemCount == 0
                binding.loader.isVisible = isInitializing
                binding.content.isVisible = !isInitializing
            }
        }.withLoadStateFooter(loadStateFooterAdapter)
    }

    private fun refreshViewBinding(view: SwipeRefreshLayout) = with(view) {
        setOnRefreshListener {
            fetchCurrentData()
            isRefreshing = false
        }
    }

    private fun fetchCurrentData() {
        val (month, year) = with(LocalDate.now()) {
            month.name to year
        }
        viewModel.fetchReleasesList(month, year)
    }
}

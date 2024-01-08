package tech.dalapenko.feature.premieres.view

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tech.dalapenko.core.basepresentation.navigate.Animation
import tech.dalapenko.core.basepresentation.navigate.Deeplink
import tech.dalapenko.core.basepresentation.view.sectionrecycler.SectionRecyclerAdapter
import tech.dalapenko.premieres.R
import tech.dalapenko.premieres.databinding.PremieresBinding
import tech.dalapenko.feature.premieres.viewmodel.PremieresViewModel
import tech.dalapenko.feature.premieres.model.UiState
import java.time.LocalDate

@AndroidEntryPoint
class PremieresFragment : Fragment(R.layout.premieres) {

    private var _binding: PremieresBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PremieresViewModel by viewModels()

    private val premieresRecyclerAdapter = PremieresRecyclerAdapter {
        findNavController().navigate(
            request = Deeplink.openFilmDetails(it.premiere.id),
            navOptions = Animation.slideRight(R.id.premieres_root)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PremieresBinding.inflate(inflater, container, false)
        with(binding) {
            refreshViewBinding(refresh)
            contentViewBinding(content)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.contentStateFlow.collect { state ->
                    when (state) {
                        is UiState.CurrentDataReady -> onDataReady(state.data, false)
                        is UiState.CachedDataReady -> onDataReady(state.data, true)
                        is UiState.Loading -> onDataLoading()
                        is UiState.Error -> onDataError()
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

    private fun onDataReady(premiereData: List<SectionRecyclerAdapter.Item>, isCachedData: Boolean) {
        setContentVisible()
        setCachedData(isCachedData)
        premieresRecyclerAdapter.setData(premiereData)
    }

    private fun onDataLoading() = with(binding) {
        loader.isVisible = true
        content.isVisible = false
        error.isVisible = false
    }

    private fun onDataError() = with(binding) {
        loader.isVisible = false
        content.isVisible = false
        error.isVisible = true
    }

    private fun contentViewBinding(view: RecyclerView) = with(view) {
        layoutManager = LinearLayoutManager(context)
        adapter = premieresRecyclerAdapter
    }

    private fun refreshViewBinding(view: SwipeRefreshLayout) = with(view) {
        setOnRefreshListener {
            fetchCurrentData()
            isRefreshing = false
        }
    }

    private fun setContentVisible() = with(binding) {
        loader.isVisible = false
        error.isVisible = false
        content.isVisible = true
    }

    private fun setCachedData(boolean: Boolean) = with(binding) {
        cachedState.isVisible = boolean
    }

    private fun fetchCurrentData() {
        val (month, year) = with(LocalDate.now()) {
            month.name to year
        }
        viewModel.fetchContentList(month, year)
    }
}
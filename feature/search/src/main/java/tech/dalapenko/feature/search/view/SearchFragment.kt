package tech.dalapenko.feature.search.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import tech.dalapenko.core.basepresentation.navigate.Animation
import tech.dalapenko.core.basepresentation.navigate.Deeplink
import tech.dalapenko.feature.search.R
import tech.dalapenko.feature.search.databinding.SearchBinding
import tech.dalapenko.feature.search.model.UiState
import tech.dalapenko.feature.search.viewmodel.SearchViewModel

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.search) {

    private var _binding: SearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()

    private val searchPagingAdapter by lazy(LazyThreadSafetyMode.NONE) {
        SearchPagingAdapter { view, item ->
            showInputMethod(view, false)
            findNavController().navigate(
                request = Deeplink.openFilmDetails(item.id),
                navOptions = Animation.slideRight(R.id.search_root)
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchBinding.inflate(inflater, container, false)
        with(binding) {
            searchViewBiding(searchView)
            contentViewBinding(content)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiStateFlow.collectLatest(::onUiStateChange)
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private suspend fun onUiStateChange(uiState: UiState) = with(binding) {
        content.isVisible = uiState.isNetworkAvailable
        error.isVisible = !uiState.isNetworkAvailable

        if (!uiState.isNetworkAvailable) return@with

        uiState.pagingDataFlow.collectLatest(searchPagingAdapter::submitData)
    }

    private fun searchViewBiding(view: CustomSearchView) = with(view) {
        setOnQueryTextFocusChangeListener { view, hasFocus ->
            if (hasFocus) showInputMethod(view.findFocus())
        }
        setOnBackButtonPressClickListener {
            parentFragmentManager.popBackStack()
        }
        setOnQueryChangeListener(viewModel::updateQuery)
    }

    private fun contentViewBinding(view: RecyclerView) = with(view) {
        layoutManager = LinearLayoutManager(context)
        adapter = searchPagingAdapter.withLoadStateFooter(SearchPagingLoadStateAdapter())
    }

    private fun showInputMethod(view: View, isShow: Boolean = true) {
        val imm = getSystemService(requireContext(), InputMethodManager::class.java)

        if (isShow) {
            imm?.showSoftInput(view, 0)
        } else {
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}

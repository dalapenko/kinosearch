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
import kotlinx.coroutines.launch
import tech.dalapenko.core.basepresentation.navigate.Animation
import tech.dalapenko.core.basepresentation.navigate.Deeplink
import tech.dalapenko.data.search.model.SearchResult
import tech.dalapenko.feature.search.R
import tech.dalapenko.feature.search.databinding.SearchBinding
import tech.dalapenko.feature.search.viewmodel.UiState
import tech.dalapenko.feature.search.viewmodel.SearchViewModel

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.search) {

    private var _binding: SearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()

    private val searchResultAdapter = SearchRecyclerAdapter {
        findNavController().navigate(
            request = Deeplink.openFilmDetails(it.id),
            navOptions = Animation.slideRight(R.id.search_root)
        )
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
                viewModel.viewStateFlow.collect { state ->
                    when (state) {
                        is UiState.Ready -> onDataReady(state.data)
                        is UiState.Loading -> onDataLoading()
                        is UiState.Error -> onDataError()
                    }
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onDataReady(searchResultList: List<SearchResult>) {
        setContentVisible()
        searchResultAdapter.setData(searchResultList)
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


    private fun searchViewBiding(view: CustomSearchView) = with(view) {
        setOnQueryTextFocusChangeListener { view, hasFocus ->
            if (hasFocus) showInputMethod(view.findFocus())
        }
        setOnBackButtonPressClickListener {
            parentFragmentManager.popBackStack()
        }
        setOnQueryChangeListener {
            viewModel.updateQuery(it)
        }
    }

    private fun contentViewBinding(view: RecyclerView) = with(view) {
        layoutManager = LinearLayoutManager(context)
        adapter = searchResultAdapter
    }


    private fun setContentVisible() = with(binding) {
        loader.isVisible = false
        error.isVisible = false
        content.isVisible = true
    }

    private fun showInputMethod(view: View) {
        val imm = getSystemService(requireContext(), InputMethodManager::class.java)
        imm?.showSoftInput(view, 0)
    }
}

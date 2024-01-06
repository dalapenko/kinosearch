package tech.dalapenko.search.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import tech.dalapenko.search.R
import tech.dalapenko.search.databinding.SearchBinding
import tech.dalapenko.search.model.State
import tech.dalapenko.search.viewmodel.SearchViewModel


@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.search) {

    private var _binding: SearchBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchBinding.inflate(inflater, container, false)
        with(binding.searchView) {
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

        binding.content.layoutManager = LinearLayoutManager(context)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.viewStateFlow.collect { state ->
                    when (state) {
                        is State.Success -> {
                            binding.loader.isVisible = false
                            binding.error.isVisible = false
                            binding.content.isVisible = true
                            binding.content.adapter = SearchRecyclerAdapter(state.data)
                        }
                        is State.Loading -> {
                            binding.loader.isVisible = true
                            binding.content.isVisible = false
                            binding.error.isVisible = false
                        }
                        is State.Error -> {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showInputMethod(view: View) {
        val imm = getSystemService(requireContext(), InputMethodManager::class.java)
        imm?.showSoftInput(view, 0)
    }
}

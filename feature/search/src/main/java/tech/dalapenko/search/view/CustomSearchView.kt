package tech.dalapenko.search.view

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.SearchView.OnQueryTextListener
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import tech.dalapenko.search.databinding.CustomSearchViewBinding

class CustomSearchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private var _binding: CustomSearchViewBinding? = null
    private val binding get() = _binding!!

    init {
        _binding = CustomSearchViewBinding.inflate(LayoutInflater.from(context), this, true)
        binding.clearButton.setOnClickListener {
            binding.query.text.clear()
        }
    }

    fun setOnQueryTextFocusChangeListener(onQueryTextFocusChangeListener: OnFocusChangeListener) {
        binding.query.onFocusChangeListener = onQueryTextFocusChangeListener
    }

    fun setOnBackButtonPressClickListener(onClickListener: OnClickListener) {
        binding.backButton.setOnClickListener(onClickListener)
    }

    fun setOnQueryChangeListener(onQueryTextChangeListener: OnQueryTextChangeListener) {
        binding.query.doOnTextChanged { text, _, _, _ ->
            onQueryTextChangeListener.onTextChange(text)
        }
    }

//    fun getQueryTextChangeStateFlow(): StateFlow<CharSequence?> {
//        val query: MutableStateFlow<CharSequence?> = MutableStateFlow(null)
//
//        binding.query.doOnTextChanged { text, _, _, _ ->
//            query.value = text
//        }
//
//        return query
//    }

    fun interface OnQueryTextChangeListener {

        fun onTextChange(text: CharSequence?)
    }


}
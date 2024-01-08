package tech.dalapenko.feature.search.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.core.widget.doOnTextChanged
import tech.dalapenko.feature.search.databinding.CustomSearchViewBinding

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

    fun interface OnQueryTextChangeListener {

        fun onTextChange(text: CharSequence?)
    }


}
package tech.dalapenko.feature.releases.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import tech.dalapenko.core.basepresentation.R
import tech.dalapenko.feature.releases.databinding.ReleaseItemLoadStateBinding

class ReleasePagingLoadStateAdapter
    : LoadStateAdapter<ReleasePagingLoadStateAdapter.LoadStateItemViewHolder>() {

    override fun onBindViewHolder(
        holder: LoadStateItemViewHolder,
        loadState: LoadState
    ) = with(holder.binding) {
        loader.isVisible = loadState is LoadState.Loading
        error.isVisible = loadState is LoadState.Error

        if (loadState !is LoadState.Error) return

        loadState.error.localizedMessage
            ?.let(error::setText)
            ?: error.setText(R.string.data_error_message)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadStateItemViewHolder {
        return LoadStateItemViewHolder(
            ReleaseItemLoadStateBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    class LoadStateItemViewHolder(
        val binding: ReleaseItemLoadStateBinding
    ) : RecyclerView.ViewHolder(binding.root)
}

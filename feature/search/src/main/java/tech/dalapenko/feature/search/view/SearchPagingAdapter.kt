package tech.dalapenko.feature.search.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import tech.dalapenko.core.basepresentation.utils.loadImage
import tech.dalapenko.data.search.model.SearchResult
import tech.dalapenko.feature.search.databinding.SearchItemBinding

class SearchPagingAdapter(
    private val onItemClickListener: OnSearchResultClicked
) : PagingDataAdapter<SearchResult, SearchPagingAdapter.SearchItemViewHolder>(SearchResultItemCallback) {

    class SearchItemViewHolder(val item: SearchItemBinding) : RecyclerView.ViewHolder(item.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        return SearchItemViewHolder(
            SearchItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        val data = getItem(position) ?: return

        with(holder.item) {
            cover.loadImage(data.posterUrlPreview)
            ruTitle.text = data.ruName
            originTitle.text = data.originName

            root.setOnClickListener {
                onItemClickListener.onItemClicked(this.root, data)
            }
        }
    }

    fun interface OnSearchResultClicked {
        fun onItemClicked(view: View, item: SearchResult)
    }
}

private object SearchResultItemCallback : DiffUtil.ItemCallback<SearchResult>() {
    override fun areItemsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean {
        return oldItem == newItem
    }
}

package tech.dalapenko.feature.search.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tech.dalapenko.core.basepresentation.utils.loadImage
import tech.dalapenko.data.search.model.SearchResult
import tech.dalapenko.feature.search.databinding.SearchItemBinding

class SearchRecyclerAdapter(
    private val recyclerItemList: List<SearchResult>,
    private val onItemClickListener: OnSearchResultClicked
) : RecyclerView.Adapter<SearchRecyclerAdapter.SearchItemViewHolder>() {

    class SearchItemViewHolder(val item: SearchItemBinding) : RecyclerView.ViewHolder(item.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        return SearchItemViewHolder(
            SearchItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return recyclerItemList.size
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        val data = recyclerItemList[position]

        with(holder.item) {
            cover.loadImage(data.posterUrlPreview)
            ruTitle.text = data.ruName
            originTitle.text = data.originName

            root.setOnClickListener { onItemClickListener.onItemClicked(data) }
        }
    }

    fun interface OnSearchResultClicked {
        fun onItemClicked(item: SearchResult)
    }
}

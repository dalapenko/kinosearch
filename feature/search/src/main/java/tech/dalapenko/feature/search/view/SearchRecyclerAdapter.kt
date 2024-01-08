package tech.dalapenko.feature.search.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import tech.dalapenko.core.basepresentation.utils.loadImage
import tech.dalapenko.data.search.model.SearchResult
import tech.dalapenko.feature.search.databinding.SearchItemBinding

class SearchRecyclerAdapter(
    private val onItemClickListener: OnSearchResultClicked
) : RecyclerView.Adapter<SearchRecyclerAdapter.SearchItemViewHolder>() {

    private val recyclerItemList: MutableList<SearchResult> = mutableListOf()

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

            root.setOnClickListener {
                onItemClickListener.onItemClicked(this.root, data)
            }
        }
    }

    fun setData(searchResultList: List<SearchResult>) {
        val diffCallback = SearchResultDiffUtil(recyclerItemList, searchResultList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        recyclerItemList.clear()
        recyclerItemList.addAll(searchResultList)
        diffResult.dispatchUpdatesTo(this)
    }

    fun interface OnSearchResultClicked {
        fun onItemClicked(view: View, item: SearchResult)
    }
}

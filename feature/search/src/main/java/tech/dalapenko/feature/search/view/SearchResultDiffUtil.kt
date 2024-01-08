package tech.dalapenko.feature.search.view

import androidx.recyclerview.widget.DiffUtil
import tech.dalapenko.data.search.model.SearchResult

class SearchResultDiffUtil(
    private val oldContent: List<SearchResult>,
    private val newContent: List<SearchResult>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldContent.size
    }

    override fun getNewListSize(): Int {
        return newContent.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldContent[oldItemPosition].id == newContent[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldContent[oldItemPosition] == newContent[newItemPosition]
    }
}
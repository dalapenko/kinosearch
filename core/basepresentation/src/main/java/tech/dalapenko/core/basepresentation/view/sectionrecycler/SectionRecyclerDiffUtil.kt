package tech.dalapenko.core.basepresentation.view.sectionrecycler

import androidx.recyclerview.widget.DiffUtil

class SectionRecyclerDiffUtil(
    private val oldContent: List<SectionRecyclerAdapter.Item>,
    private val newContent: List<SectionRecyclerAdapter.Item>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldContent.size
    }

    override fun getNewListSize(): Int {
        return newContent.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldContent[oldItemPosition]
        val newItem = newContent[newItemPosition]

        if (oldItem.type != newItem.type) return false

        return oldItem.areItemsTheSame(newItem)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldContent[oldItemPosition]
        val newItem = newContent[newItemPosition]

        if (oldItem.type != newItem.type) return false

        return oldItem.areContentsTheSame(newItem)
    }
}
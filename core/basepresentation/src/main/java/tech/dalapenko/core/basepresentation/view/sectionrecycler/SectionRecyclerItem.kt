package tech.dalapenko.core.basepresentation.view.sectionrecycler

abstract class SectionRecyclerItem<T : SectionRecyclerItem<T>>(
    override val type: SectionRecyclerAdapter.ItemType = SectionRecyclerAdapter.ItemType.ITEM
) : SectionRecyclerAdapter.Item {

    fun interface OnItemClicked<T> {
        fun onItemClicked(item: T)
    }

    abstract fun areContentsTheSame(other: T): Boolean

    abstract fun areItemsTheSame(other: T): Boolean

    @Suppress("UNCHECKED_CAST")
    final override fun areContentsTheSame(other: SectionRecyclerAdapter.Item): Boolean {
        return areContentsTheSame(other as T)
    }

    @Suppress("UNCHECKED_CAST")
    final override fun areItemsTheSame(other: SectionRecyclerAdapter.Item): Boolean {
        return areItemsTheSame(other as T)
    }
}
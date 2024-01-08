package tech.dalapenko.core.basepresentation.view.sectionrecycler

abstract class SectionRecyclerItem(
    override val type: SectionRecyclerAdapter.ItemType = SectionRecyclerAdapter.ItemType.ITEM
) : SectionRecyclerAdapter.Item {

    fun interface OnItemClicked<T : SectionRecyclerItem> {
        fun onItemClicked(item: T)
    }
}
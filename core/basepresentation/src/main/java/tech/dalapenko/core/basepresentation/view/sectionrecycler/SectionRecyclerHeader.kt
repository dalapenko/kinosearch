package tech.dalapenko.core.basepresentation.view.sectionrecycler

abstract class SectionRecyclerHeader(
    override val type: SectionRecyclerAdapter.ItemType = SectionRecyclerAdapter.ItemType.HEADER
) : SectionRecyclerAdapter.Item {

    fun interface OnHeaderClicked<T : SectionRecyclerHeader> {
        fun onClicked(item: T)
    }
}

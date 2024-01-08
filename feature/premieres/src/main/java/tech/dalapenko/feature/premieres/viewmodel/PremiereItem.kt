package tech.dalapenko.feature.premieres.viewmodel

import tech.dalapenko.core.basepresentation.view.sectionrecycler.SectionRecyclerAdapter
import tech.dalapenko.core.basepresentation.view.sectionrecycler.SectionRecyclerItem
import tech.dalapenko.data.premieres.model.Premiere

class PremiereItem(
    val premiere: Premiere
) : SectionRecyclerItem<PremiereItem>(SectionRecyclerAdapter.ItemType.ITEM) {
    override fun areContentsTheSame(other: PremiereItem): Boolean {
        return premiere.id == other.premiere.id
    }

    override fun areItemsTheSame(other: PremiereItem): Boolean {
        return premiere == other.premiere
    }
}
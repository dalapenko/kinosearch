package tech.dalapenko.feature.releases.viewmodel

import tech.dalapenko.core.basepresentation.view.sectionrecycler.SectionRecyclerAdapter
import tech.dalapenko.core.basepresentation.view.sectionrecycler.SectionRecyclerItem
import tech.dalapenko.data.releases.model.Release

class ReleaseItem(
    val release: Release
) : SectionRecyclerItem<ReleaseItem>(SectionRecyclerAdapter.ItemType.ITEM) {

    override fun areContentsTheSame(other: ReleaseItem): Boolean {
        return release.id == other.release.id
    }

    override fun areItemsTheSame(other: ReleaseItem): Boolean {
        return release == other.release
    }
}
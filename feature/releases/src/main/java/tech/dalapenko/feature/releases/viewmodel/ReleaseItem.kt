package tech.dalapenko.feature.releases.viewmodel

import tech.dalapenko.core.basepresentation.view.sectionrecycler.SectionRecyclerAdapter
import tech.dalapenko.core.basepresentation.view.sectionrecycler.SectionRecyclerItem
import tech.dalapenko.data.releases.model.Release

class ReleaseItem(
    val release: Release
) : SectionRecyclerItem(SectionRecyclerAdapter.ItemType.ITEM)
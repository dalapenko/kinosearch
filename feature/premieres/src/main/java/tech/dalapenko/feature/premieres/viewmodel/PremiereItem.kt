package tech.dalapenko.feature.premieres.viewmodel

import tech.dalapenko.core.basepresentation.view.sectionrecycler.SectionRecyclerAdapter
import tech.dalapenko.core.basepresentation.view.sectionrecycler.SectionRecyclerItem
import tech.dalapenko.data.premieres.model.Premiere

class PremiereItem(
    val premiere: Premiere
) : SectionRecyclerItem(SectionRecyclerAdapter.ItemType.ITEM)
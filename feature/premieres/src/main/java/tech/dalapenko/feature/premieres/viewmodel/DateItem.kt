package tech.dalapenko.feature.premieres.viewmodel

import tech.dalapenko.core.basepresentation.view.sectionrecycler.SectionRecyclerAdapter
import tech.dalapenko.core.basepresentation.view.sectionrecycler.SectionRecyclerHeader
import tech.dalapenko.core.basepresentation.view.sectionrecycler.SectionRecyclerItem

class DateItem (
    val date: String?
) : SectionRecyclerHeader(SectionRecyclerAdapter.ItemType.HEADER)
package tech.dalapenko.feature.releases.model

import tech.dalapenko.core.basepresentation.view.sectionrecycler.SectionRecyclerAdapter
import tech.dalapenko.core.basepresentation.view.sectionrecycler.SectionRecyclerHeader

class DateItem (
    val date: String?
) : SectionRecyclerHeader<DateItem>(SectionRecyclerAdapter.ItemType.HEADER) {

    override fun areContentsTheSame(other: DateItem): Boolean {
        return date == other.date
    }

    override fun areItemsTheSame(other: DateItem): Boolean {
        return date == other.date
    }
}
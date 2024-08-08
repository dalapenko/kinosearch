package tech.dalapenko.feature.releases.model

import tech.dalapenko.data.releases.model.Release

sealed class ReleasePagingItem(val viewType: Int) {

    class ReleaseItem(val release: Release) : ReleasePagingItem(RELEASE_ITEM_VIEW_TYPE)
    class DateSeparatorItem(val date: String) : ReleasePagingItem(DATE_SEPARATOR_ITEM_VIEW_TYPE)

    fun interface OnItemClicked<T : ReleasePagingItem> {
        fun onItemClicked(item: T)
    }

    companion object {
        const val RELEASE_ITEM_VIEW_TYPE = 1
        const val DATE_SEPARATOR_ITEM_VIEW_TYPE = 2
        const val ITEM_VIEW_TYPE_NOT_SUPPORTED = -1
    }
}
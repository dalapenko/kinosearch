package tech.dalapenko.releases.viewmodel

import tech.dalapenko.releases.model.entity.Release

sealed class ReleaseListItem(val type: Int) {

    class DateItem(
        val date: String?
    ) : ReleaseListItem(DATE_VIEW_TYPE)

    class ReleaseItem(
        val release: Release
    ) : ReleaseListItem(PREMIERE_VIEW_TYPE)

    companion object {
        const val DATE_VIEW_TYPE = 0
        const val PREMIERE_VIEW_TYPE = 1
    }
}


package tech.dalapenko.premieres.viewmodel

import tech.dalapenko.premieres.model.entity.Premiere

sealed class PremiereListItem(val type: Int) {

    class DateItem(
        val date: String?
    ) : PremiereListItem(DATE_VIEW_TYPE)

    class PremiereItem(
        val premiere: Premiere
    ) : PremiereListItem(PREMIERE_VIEW_TYPE)

    companion object {
        const val DATE_VIEW_TYPE = 0
        const val PREMIERE_VIEW_TYPE = 1
    }
}


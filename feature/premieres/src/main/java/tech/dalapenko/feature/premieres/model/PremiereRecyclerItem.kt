package tech.dalapenko.feature.premieres.model

import tech.dalapenko.data.premieres.model.Premiere

sealed class PremiereRecyclerItem(val viewType: Int) {

    class PremiereItem(val premiere: Premiere) : PremiereRecyclerItem(PREMIERE_ITEM_VIEW_TYPE)
    class DateSeparatorItem(val date: String?) : PremiereRecyclerItem(DATE_SEPARATOR_ITEM_VIEW_TYPE)

    fun interface OnItemClicked<T> {
        fun onItemClicked(item: T)
    }

    companion object {
        const val PREMIERE_ITEM_VIEW_TYPE = 1
        const val DATE_SEPARATOR_ITEM_VIEW_TYPE = 2
        const val ITEM_VIEW_TYPE_NOT_SUPPORTED = -1
    }
}
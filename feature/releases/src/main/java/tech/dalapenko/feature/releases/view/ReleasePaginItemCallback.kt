package tech.dalapenko.feature.releases.view

import androidx.recyclerview.widget.DiffUtil
import tech.dalapenko.feature.releases.model.ReleasePagingItem

internal object ReleasePagingItemCallback : DiffUtil.ItemCallback<ReleasePagingItem>() {

    override fun areItemsTheSame(
        oldItem: ReleasePagingItem,
        newItem: ReleasePagingItem
    ): Boolean {
        return when {
            oldItem is ReleasePagingItem.ReleaseItem
                    && newItem is ReleasePagingItem.ReleaseItem -> {
                oldItem.release.id == newItem.release.id
            }

            oldItem is ReleasePagingItem.DateSeparatorItem
                    && newItem is ReleasePagingItem.DateSeparatorItem -> {
                oldItem.date === newItem.date
            }

            else -> false
        }
    }

    override fun areContentsTheSame(
        oldItem: ReleasePagingItem,
        newItem: ReleasePagingItem
    ): Boolean {
        return when {
            oldItem is ReleasePagingItem.ReleaseItem
                    && newItem is ReleasePagingItem.ReleaseItem -> {
                oldItem.release == newItem.release
            }

            oldItem is ReleasePagingItem.DateSeparatorItem
                    && newItem is ReleasePagingItem.DateSeparatorItem -> {
                oldItem.date == newItem.date
            }

            else -> false
        }
    }
}

package tech.dalapenko.feature.releases.view

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import tech.dalapenko.feature.releases.databinding.ReleaseDateSeparatorItemBinding
import tech.dalapenko.feature.releases.databinding.ReleaseItemBinding

sealed class ReleasePagingViewHolder(
    val item: ViewBinding
) : RecyclerView.ViewHolder(item.root) {

    class ReleaseItemViewHolder(
        item: ReleaseItemBinding
    ) : ReleasePagingViewHolder(item)

    class DateSeparatorItemViewHolder(
        item: ReleaseDateSeparatorItemBinding
    ) : ReleasePagingViewHolder(item)
}
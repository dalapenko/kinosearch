package tech.dalapenko.feature.releases.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import tech.dalapenko.core.basepresentation.utils.loadImage
import tech.dalapenko.feature.releases.databinding.ReleaseDateSeparatorItemBinding
import tech.dalapenko.feature.releases.databinding.ReleaseItemBinding
import tech.dalapenko.feature.releases.model.ReleasePagingItem
import tech.dalapenko.feature.releases.model.ReleasePagingItem.DateSeparatorItem
import tech.dalapenko.feature.releases.model.ReleasePagingItem.ReleaseItem
import tech.dalapenko.feature.releases.view.ReleasePagingViewHolder.DateSeparatorItemViewHolder
import tech.dalapenko.feature.releases.view.ReleasePagingViewHolder.ReleaseItemViewHolder

class ReleasePagingAdapter(
    private val onReleaseItemClickListener: ReleasePagingItem.OnItemClicked<ReleaseItem>
) : PagingDataAdapter<ReleasePagingItem, ReleasePagingViewHolder>(ReleasePagingItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReleasePagingViewHolder {
        return when (viewType) {
            ReleasePagingItem.RELEASE_ITEM_VIEW_TYPE -> onCreateReleaseViewHolder(parent)
            ReleasePagingItem.DATE_SEPARATOR_ITEM_VIEW_TYPE -> onCreateDateSeparatorViewHolder(
                parent
            )

            else -> throw NotSupportedAdapterItemType()
        }
    }

    override fun onBindViewHolder(holder: ReleasePagingViewHolder, position: Int) {
        val data = getItem(position) ?: return

        when {
            holder is ReleaseItemViewHolder && data is ReleaseItem -> {
                onBindReleaseViewHolder(holder, data)
            }

            holder is DateSeparatorItemViewHolder && data is DateSeparatorItem -> {
                onBindDateSeparatorViewHolder(holder, data)
            }
        }
    }

    private fun onBindReleaseViewHolder(
        holder: ReleaseItemViewHolder,
        releaseItem: ReleaseItem
    ) {
        val data = releaseItem.release
        with(holder.item as ReleaseItemBinding) {
            cover.loadImage(data.posterUrlPreview)
            ruTitle.text = data.ruName
            originTitle.text = data.originName
            root.setOnClickListener {
                onReleaseItemClickListener.onItemClicked(releaseItem)
            }
        }
    }

    private fun onBindDateSeparatorViewHolder(
        holder: DateSeparatorItemViewHolder,
        dateSeparatorItem: DateSeparatorItem
    ) {
        (holder.item as ReleaseDateSeparatorItemBinding).groupDate.text = dateSeparatorItem.date
    }

    private fun onCreateReleaseViewHolder(
        parent: ViewGroup
    ): ReleaseItemViewHolder {
        return ReleaseItemViewHolder(
            ReleaseItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    private fun onCreateDateSeparatorViewHolder(
        parent: ViewGroup
    ): DateSeparatorItemViewHolder {
        return DateSeparatorItemViewHolder(
            ReleaseDateSeparatorItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position)?.viewType ?: ReleasePagingItem.ITEM_VIEW_TYPE_NOT_SUPPORTED
    }

    private class NotSupportedAdapterItemType : Error()
}

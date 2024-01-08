package tech.dalapenko.feature.releases.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tech.dalapenko.core.basepresentation.utils.loadImage
import tech.dalapenko.core.basepresentation.view.sectionrecycler.SectionRecyclerAdapter
import tech.dalapenko.core.basepresentation.view.sectionrecycler.SectionRecyclerItem
import tech.dalapenko.feature.releases.databinding.ReleaseDateItemBinding
import tech.dalapenko.feature.releases.databinding.ReleaseItemBinding
import tech.dalapenko.feature.releases.model.DateItem
import tech.dalapenko.feature.releases.model.ReleaseItem

class ReleaseRecyclerAdapter(
    private val onItemClickListener: SectionRecyclerItem.OnItemClicked<ReleaseItem>
) :  SectionRecyclerAdapter<
        ReleaseRecyclerAdapter.DateViewHolder, DateItem,
        ReleaseRecyclerAdapter.ReleaseViewHolder, ReleaseItem>() {

    class ReleaseViewHolder(val item: ReleaseItemBinding) : RecyclerView.ViewHolder(item.root)
    class DateViewHolder(val item: ReleaseDateItemBinding) : RecyclerView.ViewHolder(item.root)

    override fun onBindHeaderViewHolder(holder: DateViewHolder, data: DateItem) {
        holder.item.groupDate.text = data.date
    }

    override fun onBindItemViewHolder(holder: ReleaseViewHolder, data: ReleaseItem) {
        val premierData = data.release

        with(holder.item) {
            cover.loadImage(premierData.posterUrlPreview)
            ruTitle.text = premierData.ruName
            originTitle.text = premierData.originName

            root.setOnClickListener { onItemClickListener.onItemClicked(data) }
        }
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup, viewType: ItemType): DateViewHolder {
        return DateViewHolder(
            ReleaseDateItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: ItemType): ReleaseViewHolder {
        return ReleaseViewHolder(
            ReleaseItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
}

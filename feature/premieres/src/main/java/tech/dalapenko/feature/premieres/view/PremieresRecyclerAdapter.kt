package tech.dalapenko.feature.premieres.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tech.dalapenko.core.basepresentation.utils.loadImage
import tech.dalapenko.core.basepresentation.view.sectionrecycler.SectionRecyclerAdapter
import tech.dalapenko.core.basepresentation.view.sectionrecycler.SectionRecyclerItem
import tech.dalapenko.feature.premieres.model.DateItem
import tech.dalapenko.feature.premieres.model.PremiereItem
import tech.dalapenko.premieres.databinding.PremiereDateItemBinding
import tech.dalapenko.premieres.databinding.PremiereItemBinding

class PremieresRecyclerAdapter(
    private val onItemClickListener: SectionRecyclerItem.OnItemClicked<PremiereItem>
) : SectionRecyclerAdapter<
        PremieresRecyclerAdapter.DateViewHolder, DateItem,
        PremieresRecyclerAdapter.PremiereViewHolder, PremiereItem>() {

    class DateViewHolder(val item: PremiereDateItemBinding) : RecyclerView.ViewHolder(item.root)
    class PremiereViewHolder(val item: PremiereItemBinding) : RecyclerView.ViewHolder(item.root)

    override fun onBindHeaderViewHolder(holder: DateViewHolder, data: DateItem) {
        holder.item.premiereDate.text = data.date
    }

    override fun onBindItemViewHolder(holder: PremiereViewHolder, data: PremiereItem) {
        val premierData = data.premiere

        with(holder.item) {
            cover.loadImage(premierData.posterUrlPreview)
            ruTitle.text = premierData.ruName
            originTitle.text = premierData.originName

            root.setOnClickListener { onItemClickListener.onItemClicked(data) }
        }
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup, viewType: ItemType): DateViewHolder {
        return DateViewHolder(
            PremiereDateItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: ItemType): PremiereViewHolder {
        return PremiereViewHolder(
            PremiereItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
}
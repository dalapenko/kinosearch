package tech.dalapenko.feature.premieres.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import tech.dalapenko.core.basepresentation.utils.loadImage
import tech.dalapenko.feature.premieres.model.PremiereRecyclerItem
import tech.dalapenko.feature.premieres.model.PremiereRecyclerItem.DateSeparatorItem
import tech.dalapenko.feature.premieres.model.PremiereRecyclerItem.PremiereItem
import tech.dalapenko.feature.premieres.view.PremiereViewHolder.DateSeparatorItemViewHolder
import tech.dalapenko.feature.premieres.view.PremiereViewHolder.PremiereItemViewHolder
import tech.dalapenko.premieres.databinding.PremiereDateSeparatorItemBinding
import tech.dalapenko.premieres.databinding.PremiereItemBinding

class PremieresRecyclerAdapter(
    private val onPremiereItemClickListener: PremiereRecyclerItem.OnItemClicked<PremiereItem>
) : RecyclerView.Adapter<PremiereViewHolder>() {

    private val sectionItemList: MutableList<PremiereRecyclerItem> = mutableListOf()

    fun setData(itemList: List<PremiereRecyclerItem>) {
        val diffUtilCallback = PremiereRecyclerItemDiffUtil(sectionItemList, itemList)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        sectionItemList.clear()
        sectionItemList.addAll(itemList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PremiereViewHolder {
        return when (viewType) {
            PremiereRecyclerItem.PREMIERE_ITEM_VIEW_TYPE -> onCreatePremiereViewHolder(parent)
            PremiereRecyclerItem.DATE_SEPARATOR_ITEM_VIEW_TYPE -> onCreateDateSeparatorViewHolder(
                parent
            )

            else -> throw NotSupportedAdapterItemType()
        }
    }

    override fun getItemCount(): Int {
        return sectionItemList.size
    }

    override fun onBindViewHolder(
        holder: PremiereViewHolder,
        position: Int
    ) {
        val data = sectionItemList.getOrNull(position) ?: return

        when {
            (holder is PremiereItemViewHolder) && (data is PremiereItem) -> {
                onBindPremiereViewHolder(holder, data)
            }

            holder is DateSeparatorItemViewHolder && data is DateSeparatorItem -> {
                onBindDateSeparatorViewHolder(holder, data)
            }
        }
    }

    private fun onBindPremiereViewHolder(
        holder: PremiereItemViewHolder,
        premiereItem: PremiereItem,
    ) {
        val data = premiereItem.premiere
        with(holder.item as PremiereItemBinding) {
            cover.loadImage(data.posterUrlPreview)
            ruTitle.text = data.ruName
            originTitle.text = data.originName
            root.setOnClickListener {
                onPremiereItemClickListener.onItemClicked(premiereItem)
            }
        }
    }

    private fun onBindDateSeparatorViewHolder(
        holder: DateSeparatorItemViewHolder,
        dateSeparatorItem: DateSeparatorItem
    ) {
        (holder.item as PremiereDateSeparatorItemBinding).premiereDate.text = dateSeparatorItem.date
    }

    private fun onCreatePremiereViewHolder(
        parent: ViewGroup
    ): PremiereItemViewHolder {
        return PremiereItemViewHolder(
            PremiereItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    private fun onCreateDateSeparatorViewHolder(
        parent: ViewGroup
    ): DateSeparatorItemViewHolder {
        return DateSeparatorItemViewHolder(
            PremiereDateSeparatorItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemViewType(position: Int): Int {
        return sectionItemList.getOrNull(position)?.viewType
            ?: PremiereRecyclerItem.ITEM_VIEW_TYPE_NOT_SUPPORTED
    }

    private class NotSupportedAdapterItemType : Error()
}
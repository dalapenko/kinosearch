package tech.dalapenko.core.basepresentation.view.sectionrecycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class SectionRecyclerAdapter<HEAD : RecyclerView.ViewHolder, ITEM : RecyclerView.ViewHolder>(
    private val sectionItemList: List<Item>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    abstract fun onBindHeaderViewHolder(holder: HEAD, data: SectionRecyclerHeader)
    abstract fun onBindItemViewHolder(holder: ITEM, data: SectionRecyclerItem)

    abstract fun onCreateHeaderViewHolder(parent: ViewGroup, viewType: ItemType): HEAD
    abstract fun onCreateItemViewHolder(parent: ViewGroup, viewType: ItemType): ITEM

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            ItemType.HEADER.value -> onCreateHeaderViewHolder(parent, ItemType.HEADER)
            ItemType.ITEM.value -> onCreateItemViewHolder(parent, ItemType.ITEM)
            else -> throw SectionRecyclerNotSupportedItem()
        }
    }

    final override fun getItemCount(): Int {
        return sectionItemList.size
    }

    final override fun getItemViewType(position: Int): Int {
        return sectionItemList[position].type.value
    }

    @Suppress("UNCHECKED_CAST")
    final override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = sectionItemList[position]
        when(holder.itemViewType) {
            ItemType.HEADER.value -> onBindHeaderViewHolder(holder as HEAD, data as SectionRecyclerHeader)
            ItemType.ITEM.value -> onBindItemViewHolder(holder as ITEM, data as SectionRecyclerItem)
            else -> throw throw SectionRecyclerNotSupportedItem()
        }
    }

    enum class ItemType(val value: Int) {
        HEADER(0), ITEM(1)
    }

    sealed interface Item {
        val type: ItemType
    }
}

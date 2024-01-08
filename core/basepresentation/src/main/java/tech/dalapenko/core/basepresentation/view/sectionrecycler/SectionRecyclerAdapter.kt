package tech.dalapenko.core.basepresentation.view.sectionrecycler

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class SectionRecyclerAdapter<
        HEADER_VIEW_HOLDER : RecyclerView.ViewHolder,
        HEADER_MODEL : SectionRecyclerHeader<HEADER_MODEL>,
        ITEM_VIEW_HOLDER : RecyclerView.ViewHolder,
        ITEM_MODEL :  SectionRecyclerItem<ITEM_MODEL>
    > : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val sectionItemList: MutableList<Item> = mutableListOf()

    abstract fun onBindHeaderViewHolder(holder: HEADER_VIEW_HOLDER, data: HEADER_MODEL)
    abstract fun onBindItemViewHolder(holder: ITEM_VIEW_HOLDER, data: ITEM_MODEL)

    abstract fun onCreateHeaderViewHolder(parent: ViewGroup, viewType: ItemType): HEADER_VIEW_HOLDER
    abstract fun onCreateItemViewHolder(parent: ViewGroup, viewType: ItemType): ITEM_VIEW_HOLDER

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
            ItemType.HEADER.value -> onBindHeaderViewHolder(holder as HEADER_VIEW_HOLDER, data as HEADER_MODEL)
            ItemType.ITEM.value -> onBindItemViewHolder(holder as ITEM_VIEW_HOLDER, data as ITEM_MODEL)
            else -> throw throw SectionRecyclerNotSupportedItem()
        }
    }

    fun setData(itemList: List<Item>) {
        val diffUtilCallback = SectionRecyclerDiffUtil(sectionItemList, itemList)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback)
        sectionItemList.clear()
        sectionItemList.addAll(itemList)
        diffResult.dispatchUpdatesTo(this)
    }

    enum class ItemType(val value: Int) {
        HEADER(0), ITEM(1)
    }

    sealed interface Item {
        val type: ItemType

        fun areItemsTheSame(other: Item): Boolean
        fun areContentsTheSame(other: Item): Boolean
    }
}

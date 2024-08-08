package tech.dalapenko.feature.premieres.view

import androidx.recyclerview.widget.DiffUtil
import tech.dalapenko.feature.premieres.model.PremiereRecyclerItem

class PremiereRecyclerItemDiffUtil(
    private val oldContent: List<PremiereRecyclerItem>,
    private val newContent: List<PremiereRecyclerItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldContent.size
    }

    override fun getNewListSize(): Int {
        return newContent.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldContent[oldItemPosition]
        val newItem = newContent[newItemPosition]

        return when {
            oldItem is PremiereRecyclerItem.PremiereItem
                    && newItem is PremiereRecyclerItem.PremiereItem -> {
                oldItem.premiere.id == newItem.premiere.id
            }

            oldItem is PremiereRecyclerItem.DateSeparatorItem
                    && newItem is PremiereRecyclerItem.DateSeparatorItem -> {
                oldItem.date === newItem.date
            }

            else -> false
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldContent[oldItemPosition]
        val newItem = newContent[newItemPosition]

        return when {
            oldItem is PremiereRecyclerItem.PremiereItem
                    && newItem is PremiereRecyclerItem.PremiereItem -> {
                oldItem.premiere == newItem.premiere
            }

            oldItem is PremiereRecyclerItem.DateSeparatorItem
                    && newItem is PremiereRecyclerItem.DateSeparatorItem -> {
                oldItem.date == newItem.date
            }

            else -> false
        }
    }
}
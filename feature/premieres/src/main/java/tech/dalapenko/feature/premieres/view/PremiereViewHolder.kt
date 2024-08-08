package tech.dalapenko.feature.premieres.view

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import tech.dalapenko.premieres.databinding.PremiereDateSeparatorItemBinding
import tech.dalapenko.premieres.databinding.PremiereItemBinding

sealed class PremiereViewHolder(
    val item: ViewBinding
) : RecyclerView.ViewHolder(item.root) {

    class PremiereItemViewHolder(
        item: PremiereItemBinding
    ) : PremiereViewHolder(item)

    class DateSeparatorItemViewHolder(
        item: PremiereDateSeparatorItemBinding
    ) : PremiereViewHolder(item)
}
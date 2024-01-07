package tech.dalapenko.premieres.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import tech.dalapenko.premieres.databinding.PremiereDateItemBinding
import tech.dalapenko.premieres.databinding.PremiereItemBinding
import tech.dalapenko.premieres.model.entity.Premiere
import tech.dalapenko.premieres.viewmodel.PremiereListItem

class PremieresRecyclerAdapter(
    private val recyclerItemList: List<PremiereListItem>,
    private val onItemClickListener: OnPremiereItemClicked
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class PremiereViewHolder(val item: PremiereItemBinding) : RecyclerView.ViewHolder(item.root)
    class DateViewHolder(val item: PremiereDateItemBinding) : RecyclerView.ViewHolder(item.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            PremiereListItem.DATE_VIEW_TYPE -> DateViewHolder(
                PremiereDateItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> PremiereViewHolder(
                PremiereItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return recyclerItemList[position].type
    }

    override fun getItemCount(): Int {
        return recyclerItemList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = recyclerItemList[position]
        when (holder.itemViewType) {
            PremiereListItem.DATE_VIEW_TYPE -> {
                val dateHolder = (holder as DateViewHolder).item
                val dateData = (data as PremiereListItem.DateItem).date

                with(dateHolder) {
                    premiereDate.text = dateData
                }
            }
            PremiereListItem.PREMIERE_VIEW_TYPE -> {
                val premierHolder = (holder as PremiereViewHolder).item
                val premierData = (data as PremiereListItem.PremiereItem).premiere

                with(premierHolder) {
                    cover.loadImage(premierData.posterUrlPreview)
                    ruTitle.text = premierData.ruName
                    originTitle.text = premierData.originName

                    root.setOnClickListener { onItemClickListener.onItemClicked(premierData) }
                }
            }
        }
    }

    fun interface OnPremiereItemClicked {
        fun onItemClicked(item: Premiere)
    }
}

private fun ImageView.loadImage(imageUrl: String?) {
    if (imageUrl == null) return
    Glide.with(context)
        .load(imageUrl)
        .transform(CenterCrop())
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this);
}
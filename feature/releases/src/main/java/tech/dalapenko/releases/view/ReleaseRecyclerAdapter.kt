package tech.dalapenko.releases.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import tech.dalapenko.releases.databinding.ReleaseDateItemBinding
import tech.dalapenko.releases.databinding.ReleaseItemBinding
import tech.dalapenko.releases.viewmodel.ReleaseListItem

class ReleaseRecyclerAdapter(
    private val recyclerItemList: List<ReleaseListItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class PremiereViewHolder(val item: ReleaseItemBinding) : RecyclerView.ViewHolder(item.root)
    class DateViewHolder(val item: ReleaseDateItemBinding) : RecyclerView.ViewHolder(item.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ReleaseListItem.DATE_VIEW_TYPE -> DateViewHolder(
                ReleaseDateItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> PremiereViewHolder(
                ReleaseItemBinding.inflate(
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
            ReleaseListItem.DATE_VIEW_TYPE -> {
                val dateHolder = (holder as DateViewHolder).item
                val dateData = (data as ReleaseListItem.DateItem).date

                with(dateHolder) {
                    groupDate.text = dateData
                }
            }
            ReleaseListItem.PREMIERE_VIEW_TYPE -> {
                val premierHolder = (holder as PremiereViewHolder).item
                val premierData = (data as ReleaseListItem.ReleaseItem).release

                with(premierHolder) {
                    cover.loadImage(premierData.posterUrlPreview)
                    ruTitle.text = premierData.ruName
                    originTitle.text = premierData.originName
                }
            }
        }
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
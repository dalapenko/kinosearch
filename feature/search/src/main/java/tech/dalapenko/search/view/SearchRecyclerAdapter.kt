package tech.dalapenko.search.view

import android.view.LayoutInflater
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import tech.dalapenko.search.databinding.SearchItemBinding
import tech.dalapenko.search.model.entity.SearchResult

class SearchRecyclerAdapter(
    private val recyclerItemList: List<SearchResult>,
    private val onItemClickListener: OnSearchResultClicked
) : RecyclerView.Adapter<SearchRecyclerAdapter.SearchItemViewHolder>() {

    class SearchItemViewHolder(val item: SearchItemBinding) : RecyclerView.ViewHolder(item.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        return SearchItemViewHolder(
            SearchItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return recyclerItemList.size
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        val data = recyclerItemList[position]

        with(holder.item) {
            cover.loadImage(data.posterUrlPreview)
            ruTitle.text = data.ruName
            originTitle.text = data.originName

            root.setOnClickListener { onItemClickListener.onItemClicked(data) }
        }
    }

    fun interface OnSearchResultClicked {
        fun onItemClicked(item: SearchResult)
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
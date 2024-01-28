package com.nurikhsan.tugasptcms.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.nurikhsan.tugasptcms.data.response.TrailersItem
import com.nurikhsan.tugasptcms.databinding.ItemTrailersBinding
import com.nurikhsan.tugasptcms.utils.Constant.URL_THUMBNAIL
import com.nurikhsan.tugasptcms.utils.Constant.URL_TRAILER

class AdapterTrailers: ListAdapter<TrailersItem, AdapterTrailers.ViewHolder>(TRAILER_COMPARATOR) {


    class ViewHolder(private val binding: ItemTrailersBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(trailers: TrailersItem) {

            Glide.with(itemView.context)
                .load("$URL_THUMBNAIL${trailers.key}/0.jpg")
                .transform(RoundedCorners(20))
                .into(binding.imageTrailer)

            binding.tvNameTrailers.text = trailers.name

            itemView.setOnClickListener {
                Intent(Intent.ACTION_VIEW).also { intent ->
                    intent.data = Uri.parse("$URL_TRAILER${trailers.key}")
                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTrailersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val trailers = getItem(position)
        if (trailers != null){
            holder.bind(trailers)
        }
    }
}

object TRAILER_COMPARATOR: DiffUtil.ItemCallback<TrailersItem>() {
    override fun areItemsTheSame(oldItem: TrailersItem, newItem: TrailersItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TrailersItem, newItem: TrailersItem): Boolean {
        return oldItem == newItem
    }

}

package com.nurikhsan.tugasptcms.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.nurikhsan.tugasptcms.data.response.ResultsItem
import com.nurikhsan.tugasptcms.databinding.ItemMovieBinding
import com.nurikhsan.tugasptcms.ui.activity.DetailMovieActivity
import com.nurikhsan.tugasptcms.utils.Constant.URL_POSTER
import com.nurikhsan.tugasptcms.utils.DateFormat

class AdapterMovies : ListAdapter<ResultsItem, AdapterMovies.ViewHolder>(COMPARATOR_ADAPTER) {


    class ViewHolder(private val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResultsItem) {
            binding.apply {
                tvTitle.text = item.title
                tvDate.text = DateFormat.formatDate(item.releaseDate, "dd MMMM yyyy")
                tvDesc.text = item.overview

                Glide.with(itemView.context)
                    .load(URL_POSTER + item.posterPath)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transform(RoundedCorners(20))
                    .into(imgMovie)
            }

            itemView.setOnClickListener {
                Intent(itemView.context, DetailMovieActivity::class.java).also { intent ->
                    intent.putExtra(DetailMovieActivity.MOVIE_ID, item.id)
                    itemView.context.startActivity(intent)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterMovies.ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AdapterMovies.ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null){
            holder.bind(item)
        }
    }
}

object COMPARATOR_ADAPTER: DiffUtil.ItemCallback<ResultsItem>() {
    override fun areItemsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
        return oldItem == newItem
    }

}

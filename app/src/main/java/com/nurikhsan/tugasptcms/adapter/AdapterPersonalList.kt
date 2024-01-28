package com.nurikhsan.tugasptcms.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nurikhsan.tugasptcms.R
import com.nurikhsan.tugasptcms.data.response.PersonalListItem
import com.nurikhsan.tugasptcms.databinding.ItemPersonalListBinding
import com.nurikhsan.tugasptcms.ui.activity.DetailPersonalListActivity

class AdapterPersonalList : ListAdapter<PersonalListItem, AdapterPersonalList.ViewHolder>(COMPARATOR_PERSONAL_LIST) {

    private lateinit var deleteList : DeleteList

    fun deleteList(deleteList: DeleteList){
        this.deleteList = deleteList
    }

    interface DeleteList {
        fun delete(listId: String, name: String)
    }

    inner class ViewHolder(private val binding: ItemPersonalListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(list: PersonalListItem) {
            binding.apply {
                tvNamePersonalList.text = list.name
                if (list.description.isEmpty()){
                    tvDesc.text = "-"
                }else{
                    tvDesc.text = list.description
                }
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailPersonalListActivity::class.java)
                intent.putExtra(DetailPersonalListActivity.LIST_ID, list.id)
                itemView.context.startActivity(intent)
            }

            itemView.setOnLongClickListener {
                deleteList.delete(list.id, list.name)
                return@setOnLongClickListener true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPersonalListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val list = getItem(position)
        if (list != null) {
            holder.bind(list)
        }
    }
}

object COMPARATOR_PERSONAL_LIST : DiffUtil.ItemCallback<PersonalListItem>() {
    override fun areItemsTheSame(oldItem: PersonalListItem, newItem: PersonalListItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PersonalListItem, newItem: PersonalListItem): Boolean {
        return oldItem == newItem
    }

}

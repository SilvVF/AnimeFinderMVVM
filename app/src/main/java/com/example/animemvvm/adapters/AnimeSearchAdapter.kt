package com.example.animemvvm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animemvvm.databinding.ItemAnimePreviewBinding
import com.example.animemvvm.modelsT.search.Result


class AnimeSearchAdapter() : RecyclerView.Adapter<AnimeSearchAdapter.AnimeViewHolder>() {

    inner class AnimeViewHolder(
        binding: ItemAnimePreviewBinding
    ): RecyclerView.ViewHolder(binding.root) {
        val ivAnimeImage = binding.ivAnimeImage
        val tvMembers = binding.tvMembers
        val tvRank = binding.tvRank
        val tvTitle = binding.tvTitle
    }

    private val differCallback = object : DiffUtil.ItemCallback<com.example.animemvvm.modelsT.search.Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val bind = ItemAnimePreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeViewHolder(bind)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val anime = differ.currentList[position]
        holder.apply {
            Glide.with(holder.itemView)
                .load(anime.image_url)
                .into(ivAnimeImage)
            tvMembers.text = anime.members.toString()
            tvRank.text = anime.rated
            tvTitle.text = anime.title
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(anime)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Result) -> Unit)? = null
    fun setOnItemClickListener(listener : (Result) -> Unit){
        onItemClickListener = listener
    }

}
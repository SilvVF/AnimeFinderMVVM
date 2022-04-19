package com.example.animemvvm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animemvvm.databinding.ItemAnimePreviewBinding
import com.example.animemvvm.modelsT.Top


class AnimeAdapter() : RecyclerView.Adapter<AnimeAdapter.AnimeViewHolder>() {

    inner class AnimeViewHolder(
        binding: ItemAnimePreviewBinding
    ): RecyclerView.ViewHolder(binding.root) {
        val ivAnimeImage = binding.ivAnimeImage
        val tvMembers = binding.tvMembers
        val tvRank = binding.tvRank
        val tvTitle = binding.tvTitle
        val textRank = binding.textRank
    }

    private val differCallback = object : DiffUtil.ItemCallback<Top>() {
        override fun areItemsTheSame(oldItem: Top, newItem: Top): Boolean {
            return oldItem.mal_id == newItem.mal_id
        }

        override fun areContentsTheSame(oldItem: Top, newItem: Top): Boolean {
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
            textRank.text = "Rank"
            tvRank.text = anime.rank.toString()
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

    private var onItemClickListener: ((Top) -> Unit)? = null
    fun setOnItemClickListener(listener : (Top) -> Unit){
        onItemClickListener = listener
    }

}
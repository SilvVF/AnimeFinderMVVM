package com.example.animemvvm.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.animemvvm.databinding.CardAnimeItemBinding
import com.example.animemvvm.databinding.CardAnimeItemSavedBinding
import com.example.animemvvm.modelsT.Top


class AnimeAdapterSaved() : RecyclerView.Adapter<AnimeAdapterSaved.AnimeViewHolder>() {

    inner class AnimeViewHolder(
        binding: CardAnimeItemSavedBinding
    ): RecyclerView.ViewHolder(binding.root) {
        val ivAnimeImage = binding.ivAnimeImage
        val tvMembers = binding.tvMembers
        val tvScore = binding.tvScore
        val tvTitle = binding.tvTitle
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
        val bind = CardAnimeItemSavedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeViewHolder(bind)
    }

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val anime = differ.currentList[position]
        holder.apply {
            Glide.with(holder.itemView)
                .load(anime.image_url)
                .into(ivAnimeImage)
            tvMembers.text = anime.members.toString()
            tvScore.text = anime.score.toString()
            findScoreTextColor(anime.score, tvScore)
            tvTitle.text = anime.title
        }
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(anime)
            }
        }
    }

    private fun findScoreTextColor(score: Double?, textView: TextView) {
        score?.let {
            if (score < 7){
                textView.setTextColor(Color.RED)
            } else if (score in 7.0..8.5){
                textView.setTextColor(Color.YELLOW)
            } else {
                textView.setTextColor(Color.GREEN)
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
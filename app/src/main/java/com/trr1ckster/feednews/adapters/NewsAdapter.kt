package com.trr1ckster.feednews.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.trr1ckster.feednews.R
import com.trr1ckster.feednews.databinding.ItemArticleBinding
import com.trr1ckster.feednews.data.model.Article
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter() : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(val binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root)

    val differ = AsyncListDiffer(this, ItemCallBack)

    object ItemCallBack : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemArticleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this)
                .load(news.urlToImage)
                .error(R.drawable.ic_baseline_image_24)
                .into(holder.binding.imageArticle)

        }
        holder.binding.title.text = news.title

        val date = news.publishedAt?.let {
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).parse(
                it
            )
        }
        val formattedDatesString = date?.let { SimpleDateFormat("dd.MM.yyyy", Locale.US).format(it) }

        holder.binding.articleDate.text = formattedDatesString
        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(news)
            }
        }
    }

    override fun getItemCount() = differ.currentList.size

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}

package com.example.feednews.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.feednews.databinding.ItemArticleBinding
import com.example.feednews.model.Article

class NewsAdapter() : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(
        val binding: ItemArticleBinding
    ) : RecyclerView.ViewHolder(binding.root)

    var articles = mutableListOf<Article>()
    fun setArticleList(articles: List<Article>) {
        this.articles = articles.toMutableList()
        differ.submitList(articles)
    }


    private val differ = AsyncListDiffer(this, ItemCallBack)

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
                .into(holder.binding.imageArticle)

        }
        holder.binding.title.text = news.title
        holder.binding.articleDate.text = news.publishedAt
    }

    override fun getItemCount() = differ.currentList.size
}

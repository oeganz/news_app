/*
 * Created By Ugan saripudin - oeganz1999@gmail.com
 * Copyright (c) 2021
 *
 *
 */

package com.ganz.newsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ganz.newsapp.databinding.ItemNewsBinding
import com.ganz.newsapp.repository.remote.response.Article
import com.ganz.newsapp.viewmodel.NewsVM

class ArticleAdapter(var articles:MutableList<Article> = mutableListOf(), val vm: NewsVM): RecyclerView.Adapter<ArticleAdapter.VHNews>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHNews {
            return VHNews(
               ItemNewsBinding.inflate(LayoutInflater.from(parent.context),parent,false),vm
            )
        }
        fun updateData(newArticle: List<Article>){
            val articleDiffUtils = ArticleDiffUtils(articles,newArticle)
            val diffResult = DiffUtil.calculateDiff(articleDiffUtils)
            articles.clear()
            articles.addAll(newArticle)
            diffResult.dispatchUpdatesTo(this)
        }
        fun setData(newArticle: List<Article>){
            articles = newArticle.toMutableList()
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int {
            return articles.size
        }

        override fun onBindViewHolder(holder: VHNews, position: Int) {
             holder.bind(articles[position])
        }


        class VHNews(val v:ItemNewsBinding,val vm: NewsVM):RecyclerView.ViewHolder(v.root)
        {
            fun bind(m:Article){
                v.model=m
                v.vm=vm
            }
        }

    }
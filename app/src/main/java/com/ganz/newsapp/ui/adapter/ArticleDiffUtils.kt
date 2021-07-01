/*
 * Created By Ugan saripudin - oeganz1999@gmail.com
 * Copyright (c) 2021
 *
 *
 */

package com.ganz.newsapp.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.ganz.newsapp.repository.remote.response.Article

class ArticleDiffUtils(
    val oldList: MutableList<Article>,
    val newList:List<Article>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
       return oldList.size
    }

    override fun getNewListSize(): Int {
      return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].title == newList[newItemPosition].title
                && oldList[oldItemPosition].publishedAt ==  newList[newItemPosition].publishedAt
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].title == newList[newItemPosition].title
                && oldList[oldItemPosition].publishedAt ==  newList[newItemPosition].publishedAt
    }

}
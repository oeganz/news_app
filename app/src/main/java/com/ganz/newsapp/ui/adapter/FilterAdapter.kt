/*
 * Created By Ugan saripudin - oeganz1999@gmail.com
 * Copyright (c) 2021
 *
 *
 */

package com.ganz.newsapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ganz.newsapp.databinding.ItemFilterBinding
import com.ganz.newsapp.databinding.ItemNewsBinding
import com.ganz.newsapp.repository.model.FilterModel
import com.ganz.newsapp.repository.remote.response.Article
import com.ganz.newsapp.viewmodel.FilterVM
import com.ganz.newsapp.viewmodel.NewsVM

class FilterAdapter(var filters:List<FilterModel> = mutableListOf(),val vm: FilterVM): RecyclerView.Adapter<FilterAdapter.VHFilter>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHFilter {
            return VHFilter(
               ItemFilterBinding.inflate(LayoutInflater.from(parent.context),parent,false),vm
            )
        }
        fun setData(newFilters:List<FilterModel>){
            filters=newFilters
            notifyDataSetChanged()
        }
        override fun getItemCount(): Int {
            return filters.size
        }

        override fun onBindViewHolder(holder: VHFilter, position: Int) {
             holder.bind(filters[position])
             holder.v.pos=position
        }


        class VHFilter(val v:ItemFilterBinding,val vm: FilterVM):RecyclerView.ViewHolder(v.root)
        {
            fun bind(m:FilterModel){
                v.model=m
                v.filterVM=vm
            }
        }

    }
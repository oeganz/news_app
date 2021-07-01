package com.ganz.newsapp.repository.model

import com.ganz.newsapp.ext.capitalizeFirst
import com.ganz.newsapp.repository.remote.response.Source
import java.util.*

data class FilterModel(
    val text:String="",
    val value:String?,
    var isSelected:Boolean=false,
){
    companion object {
        fun getCategories():List<FilterModel>{
            val categories = "general business entertainment health science sports technology".split(" ")
            return categories.map {
                FilterModel(it.capitalizeFirst(),it,it=="general")
            }
        }
        fun List<Source>.toFilterModel():List<FilterModel>{
            val list = mutableListOf<FilterModel>()
            list.add(FilterModel("All",null,true))
            list.addAll( map {
                FilterModel(it.name.capitalizeFirst(),it.id)
            })
            return list
        }
    }
}
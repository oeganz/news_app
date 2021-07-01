package com.ganz.newsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ganz.newsapp.repository.model.FilterModel

class FilterVM:ViewModel()
{
    val selectedFilter= MutableLiveData<FilterModel>()
    val filters = MutableLiveData<List<FilterModel>>()
    fun click (pos:Int){
        filters.value?.apply {
            //reset and set selected
            forEachIndexed { i, filterModel ->
                filterModel.isSelected = pos == i
            }
            // trigger new selected filter
            selectedFilter.postValue(this[pos])
        }?.let { filters.postValue(it) }
    }
}
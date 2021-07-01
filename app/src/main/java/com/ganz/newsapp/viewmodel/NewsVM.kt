package com.ganz.newsapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ganz.newsapp.R
import com.ganz.newsapp.base.BaseApp
import com.ganz.newsapp.ext.toErrorResponse
import com.ganz.newsapp.repository.model.FilterModel
import com.ganz.newsapp.repository.model.FilterModel.Companion.toFilterModel
import com.ganz.newsapp.repository.remote.RemoteData
import com.ganz.newsapp.repository.remote.response.Article
import com.ganz.newsapp.repository.remote.response.ResponseNews
import com.ganz.newsapp.utils.Log
import com.ganz.newsapp.utils.SingleLiveEvent
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsVM:ViewModel() {
    val categories = FilterVM()
    val sources = FilterVM()
    val search = MutableLiveData<String>()
    val listNews = MutableLiveData<MutableList<Article>>().apply { postValue(mutableListOf()) }
    val onError = SingleLiveEvent<String>()
    val onClickArticle = SingleLiveEvent<String>()


    var limitReach=false
    var isFetching = MutableLiveData<Boolean>().apply { postValue(false) }
    var currentPage=0;
    var pageSize=10

   fun init(){
       categories.filters.postValue(FilterModel.getCategories())

       categories.selectedFilter.observeForever {
           resetData()
           getSources()
           getArticles()
       }
       sources.selectedFilter.observeForever {
           resetData()
           getArticles()
       }
       search.observeForever {
           resetData()
           getArticles()
       }

       isFetching.observeForever {
         Log.d("FETCHING isFetching",it.toString())
       }

       getSources()
       getArticles()
   }
    val onSearch = fun(s:String?){
        search.postValue(s)
    }

    fun getArticles(){
        if(limitReach) {
            onError to "Limit Reach"
            return
        }
        Log.d("FETCHING","START")

        if(isFetching.value==null || isFetching.value==false)
        viewModelScope.launch {
            Log.d("FETCHING","ON GOING")
            isFetching.postValue(true)
            RemoteData.getTopHeadLine(
                category = if(sources.selectedFilter.value?.value==null) categories.selectedFilter.value?.value else null,
                sources = sources.selectedFilter.value?.value,
                search = search.value,
                page = currentPage,
                pageSize = 10)
                .catch {e->
                    e.printStackTrace()
                    onError.postValue(e.toErrorResponse().message)
                    isFetching.postValue(false)
                }
                .collect {

                    handleError(it)
                    handleNewsData(it)
                    isFetching.postValue(false)
                }

            Log.d("FETCHING","DONE")
        }

    }
    fun onScrollLast(){
        if(currentPage>0)
        {
            Log.d("SCROLLED","LAST CALLED")
            getArticles()
        }
    }
    fun setOnclickArticle(article: Article){
        onClickArticle.postValue(article.url)
    }

    private fun handleError(responseNews: ResponseNews){
        if(responseNews.status!="ok"){
            onError.postValue(responseNews.message?:BaseApp.getSingle().getString(R.string.unkown_error))
        }
    }

    private fun handleNewsData(responseNews: ResponseNews){
        responseNews.articles?.let {
            listNews.value?.apply {
                addAll(it)
            }?.let {
                listNews.postValue(it)
            }
            currentPage++
            limitReach=responseNews.totalResults<(pageSize*currentPage)
        }
    }

    fun getSources(){
        viewModelScope.launch {
            RemoteData.getSources(categories.selectedFilter.value?.value)
                .catch {e->
                    onError.postValue(e.toErrorResponse().message)
                }
                .collect {
                    sources.filters.postValue(it.sources.toFilterModel())
                }
        }
    }

    private fun resetData(){
        currentPage=0
        limitReach=false
        isFetching.postValue(false)
        listNews.postValue(mutableListOf())
    }
}
/*
 * Created By Ugan saripudin - oeganz1999@gmail.com
 * Copyright (c) 2021
 *
 *
 */

package com.ganz.newsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.ganz.newsapp.databinding.NewsFragmentBinding
import com.ganz.newsapp.ui.adapter.ArticleAdapter
import com.ganz.newsapp.ui.adapter.FilterAdapter
import com.ganz.newsapp.utils.Log
import com.ganz.newsapp.viewmodel.FilterVM
import com.ganz.newsapp.viewmodel.NewsVM
import com.google.android.material.snackbar.Snackbar

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class NewsFragment : Fragment() {



    val newsVM : NewsVM by viewModels()
    val articleAdapter  by lazy { ArticleAdapter(vm=newsVM) }
    val categoryAdapter  by lazy { FilterAdapter(vm=newsVM.categories) }
    val sourceAdapter  by lazy { FilterAdapter(vm=newsVM.sources) }
    lateinit var binding: NewsFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = NewsFragmentBinding.inflate(inflater, container, false)
        binding.vm=newsVM
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initVM()
    }

    fun initVM(){
        newsVM.init()
        viewLifecycleOwner.let {vlco->
            newsVM.onClickArticle.observe(vlco,  {
               WebViewActivity.show(requireActivity(),it)
            })
            newsVM.onError.observe(vlco,{
                Snackbar.make(
                    binding.root,
                    it,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry") { v ->
                        newsVM.getArticles()
                    }.show()
            })
            newsVM.listNews.observe(vlco,  {
                if(newsVM.currentPage<=1)
                articleAdapter.setData(it) else articleAdapter.updateData(it)
                Log.d("SIze listNews","Data ${it.size}")
            })
            newsVM.categories.filters.observe(vlco,  {
                categoryAdapter.setData(it)
                Log.d("SIze categories","Data ${it.size}")
            })
            newsVM.sources.filters.observe(vlco,  {
                sourceAdapter.setData(it)
                Log.d("SIze sources","Data ${it.size}")
            })
        }


    }

    fun initView(){
        binding.vRecArticle.adapter=articleAdapter
        binding.vRecSource.adapter=sourceAdapter
        binding.vRecCategories.adapter=categoryAdapter
    }




}
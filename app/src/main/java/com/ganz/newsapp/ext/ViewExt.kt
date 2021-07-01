package com.ganz.newsapp.ext

import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ganz.newsapp.R
import com.ganz.newsapp.utils.Log
import com.squareup.picasso.Picasso


@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    Picasso.get().load(imageUrl).placeholder(R.drawable.ic_landscape).error(R.drawable.ic_landscape).into(view)
}
@BindingAdapter("onSubmit")
fun searchListener(view: SearchView, onSubmit: (String?)->Unit) {
    view.isSubmitButtonEnabled=true
    val clearButton: ImageView = view.findViewById(androidx.appcompat.R.id.search_close_btn)
    clearButton.setOnClickListener {
        onSubmit(null)
        view.setQuery("",false)
    }

    view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
            onSubmit(query)
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            return false
        }

    })
}
@BindingAdapter("listenScroll")
fun listenScroll(recyclerView: RecyclerView,trigger:()->Unit){
    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                //reached end
                trigger()
            }
        }
    }
    recyclerView.addOnScrollListener(scrollListener)
}
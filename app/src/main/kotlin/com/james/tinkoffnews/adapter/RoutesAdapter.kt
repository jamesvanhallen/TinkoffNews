package com.james.tinkoffnews.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.james.tinkoffnews.R
import com.james.tinkoffnews.listener.NewsClickListener
import com.james.tinkoffnews.mvp.model.News
import kotlinx.android.synthetic.main.item_route.view.*
import java.util.*

class RoutesAdapter(var listener: NewsClickListener) : RecyclerView.Adapter<RoutesAdapter.ViewHolder>() {

    var mList: List<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutesAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.item_route, parent, false)
        return RoutesAdapter.ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RoutesAdapter.ViewHolder, position: Int) {
        val news = mList[position]
        holder.itemView.name.text = news.text
        holder.itemView.setOnClickListener { listener.onClick(news.id) }

    }

    override fun getItemCount(): Int {

        return mList.size
    }

    fun setItems(newses: List<News>) {
        this.mList = newses
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
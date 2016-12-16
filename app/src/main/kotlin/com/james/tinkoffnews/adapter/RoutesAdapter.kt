package com.james.tinkoffnews.adapter

import android.os.Build
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.james.tinkoffnews.R
import com.james.tinkoffnews.mvp.model.News
import kotlinx.android.synthetic.main.item_route.view.*
import org.joda.time.format.DateTimeFormat
import java.util.*
import android.text.Spanned
import com.james.tinkoffnews.Const

class RoutesAdapter() : RecyclerView.Adapter<RoutesAdapter.ViewHolder>() {

    var mList: List<News> = ArrayList()
    var listener: NewsClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutesAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = inflater.inflate(R.layout.item_route, parent, false)
        return RoutesAdapter.ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RoutesAdapter.ViewHolder, position: Int) {
        val news = mList[position]
        holder.bind(news, listener)

    }

    override fun getItemCount(): Int {

        return mList.size
    }

    fun setItems(newses: List<News>) {
        this.mList = newses
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val formatter = DateTimeFormat.forPattern(Const.DATE_FORMAT)!!

        fun bind(news: News, listener: NewsClickListener?) {
            val result: Spanned
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                result = Html.fromHtml(news.text, Html.FROM_HTML_MODE_LEGACY)
            } else {
                result = Html.fromHtml(news.text)
            }
            itemView.name.text = result

            itemView.date.text = formatter.print(news.publicationDate!!)
            itemView.card_view.setOnClickListener { listener?.onClick(news) }
        }
    }

    interface NewsClickListener {

        fun onClick(news: News)
    }
}
package com.james.tinkoffnews.view.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.james.tinkoffnews.*
import com.james.tinkoffnews.adapter.RoutesAdapter
import com.james.tinkoffnews.listener.NewsClickListener
import com.james.tinkoffnews.mvp.model.News
import com.james.tinkoffnews.mvp.presenter.NewsListPresenter
import com.james.tinkoffnews.mvp.view.NewsListView
import com.james.tinkoffnews.view.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_news_list.*

class NewsListFragment : MvpAppCompatFragment(), NewsListView, NewsClickListener {


    @InjectPresenter
    lateinit var mNewsListPresenter: NewsListPresenter

    var adapter: RoutesAdapter = RoutesAdapter(this)
    companion object {

        fun newInstance(): NewsListFragment {
            return NewsListFragment()
        }

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        App.appComponent.inject(this)
        val v = inflater?.inflate(R.layout.fragment_news_list, container, false)
        return v
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        mNewsListPresenter.presentModels(activity.isOnline())

    }

    override fun onSuccess(list: List<News>) {
        Log.d("NewsListFragment", "onSuccess")
        adapter.setItems(list)
        emptyView.hide()
        recyclerView.show()
    }

    override fun onEmptyData() {
        Log.d("NewsListFragment", "onEmpty")
        emptyView.text = getString(R.string.nothing_to_show)
        emptyView.show()
        recyclerView.hide()
    }

    override fun onError(error: String) {
        Log.e("NewsListFragment", "error $error")
        emptyView.text = error
        emptyView.show()
        recyclerView.hide()
    }

    override fun onClick(id: Int?) {

        if(id!=null && (activity as MainActivity).isOnline()) {
            (activity as MainActivity).replaceFragment(NewsContentFragment.newInstance(id))
        }
    }

}




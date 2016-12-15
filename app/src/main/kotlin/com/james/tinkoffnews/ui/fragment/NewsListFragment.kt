package com.james.tinkoffnews.ui.fragment

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
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
import com.james.tinkoffnews.ui.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_news_list.*
import java.util.*

class NewsListFragment : MvpAppCompatFragment(), NewsListView, NewsClickListener {

    @InjectPresenter
    lateinit var mNewsListPresenter: NewsListPresenter

    var adapter: RoutesAdapter = RoutesAdapter(this)

    private val refreshRun = Runnable {
        refresh.isRefreshing = true
    }


    companion object {

        fun newInstance(): NewsListFragment {
            return NewsListFragment()
        }

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        App.appComponent.inject(this)
        return inflater?.inflate(R.layout.fragment_news_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        refresh.setOnRefreshListener { onRefresh() }
        refresh.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorPrimaryDark)

        showProgress()
        //if(savedInstanceState==null) {
        presentModels()
        //  }

    }

    override fun onPause() {
        cancelProgress()
        super.onPause()
        Log.d("REF", "onPause")

    }

    private fun presentModels() {
        mNewsListPresenter.presentModels(activity.isOnline())
    }

    override fun onSuccess(list: List<News>) {
        Log.d("NewsListFragment", "onSuccess " + list.size)
        Collections.sort(list)
        adapter.setItems(list)
        cancelProgress()
    }

    override fun onEmptyData() {
        Log.d("NewsListFragment", "onEmpty")
        showSnackbar(getString(R.string.nothing_to_show))
        cancelProgress()
    }

    override fun onError(error: String) {
        Log.e("NewsListFragment", "error $error")
        showSnackbar(error)
        cancelProgress()
    }

    override fun onClick(id: Int?) {

        if (id != null && (activity as MainActivity).isOnline()) {
            (activity as MainActivity).addFragment(NewsContentFragment.newInstance(id))
        }
    }

    fun onRefresh() {
        if (!(activity as MainActivity).isOnline()) {
            showSnackbar(getString(R.string.not_online))
        } else {
            presentModels()
        }
    }

    private fun showSnackbar(text: String) {
        val snack = Snackbar.make(recyclerView, text, Snackbar.LENGTH_LONG)
        snack.setWhiteText()
        snack.show()
        snack.setAction(getString(R.string.snack_bar_action)) { presentModels()}
        cancelProgress()
    }

    fun showProgress() {
        if (refresh != null) refresh.post { refreshRun }
    }

    fun cancelProgress() {
        if (refresh != null) {
            refresh.removeCallbacks(refreshRun)
            refresh.isRefreshing = false
            refresh.destroyDrawingCache()
            refresh.clearAnimation()

        }
    }


}




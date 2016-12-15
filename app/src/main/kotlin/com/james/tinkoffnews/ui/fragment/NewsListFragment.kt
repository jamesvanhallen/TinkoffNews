package com.james.tinkoffnews.ui.fragment

import android.os.Bundle
import android.support.design.widget.Snackbar
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

class NewsListFragment : MvpAppCompatFragment(), NewsListView, NewsClickListener {

    @InjectPresenter
    lateinit var newsListPresenter: NewsListPresenter

    var adapter = RoutesAdapter()

    private val refreshRun = Runnable {
        refresh.isRefreshing = true
    }


    companion object {

        fun newInstance(): NewsListFragment {
            return NewsListFragment()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_news_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.listener = this

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        refresh.setOnRefreshListener { refreshModels() }
        refresh.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorPrimaryDark)

        showProgress()
        newsListPresenter.loadNewsFromDB()
        refreshModels()

    }

    override fun onPause() {
        cancelProgress()
        super.onPause()
        Log.d("REF", "onPause")

    }

    private fun refreshModels() {
        if (!(activity as MainActivity).isOnline()) onError(getString(R.string.not_online))
        else newsListPresenter.refresh()
    }

    override fun onSuccess(list: List<News>) {
        Log.d("NewsListFragment", "onSuccess " + list.size)
        empty.hide()
        recyclerView.show()
        adapter.setItems(list)
        cancelProgress()
    }

    override fun onEmptyData() {
        Log.d("NewsListFragment", "onEmpty")
        recyclerView.hide()
        empty.show()
        empty.text = getString(R.string.nothing_to_show)
        cancelProgress()
    }

    override fun onError(error: String) {
        Log.e("NewsListFragment", "error $error")
        showSnackbar(error)
        cancelProgress()
    }

    override fun onClick(id: Int?) {
        Log.i("NewsListFragment", "error click")
        if (id != null && (activity as MainActivity).isOnline()) {
            (activity as MainActivity).addFragment(NewsContentFragment.newInstance(id))
        }
    }

    private fun showSnackbar(text: String) {
        val snack = Snackbar.make(recyclerView, text, Snackbar.LENGTH_INDEFINITE)
        snack.setWhiteText()
        snack.show()
        snack.setAction(getString(R.string.snack_bar_action)) { refreshModels()}
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




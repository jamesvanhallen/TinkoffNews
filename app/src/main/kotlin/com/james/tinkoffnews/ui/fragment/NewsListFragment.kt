package com.james.tinkoffnews.ui.fragment

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.james.tinkoffnews.*
import com.james.tinkoffnews.adapter.RoutesAdapter
import com.james.tinkoffnews.mvp.model.News
import com.james.tinkoffnews.mvp.presenter.NewsListPresenter
import com.james.tinkoffnews.mvp.view.NewsListView
import com.james.tinkoffnews.ui.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_news_list.*

class NewsListFragment : MvpAppCompatFragment(), NewsListView {

    @InjectPresenter
    lateinit var newsListPresenter: NewsListPresenter

    lateinit var adapter: RoutesAdapter

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
        adapter = RoutesAdapter { onItemSelected(it) }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_news_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        newsListPresenter.cancelProgress()
        super.onPause()

    }

    private fun refreshModels() {
        if (!(activity as MainActivity).isOnline()) onError(Throwable(getString(R.string.not_online)))
        else newsListPresenter.refresh()
    }

    override fun onSuccess(list: List<News>) {
        empty.hide()
        recyclerView.show()
        adapter.setItems(list)
        newsListPresenter.cancelProgress()
    }

    override fun onEmptyData() {
        recyclerView.hide()
        empty.show()
        empty.text = getString(R.string.nothing_to_show)
        newsListPresenter.cancelProgress()
    }

    override fun onError(error: Throwable) {
        showSnackbar(NetworkUtils.httpErrorHandler(activity, error))
        newsListPresenter.cancelProgress()
    }

    override fun onCancelProgress() {
        if (refresh != null) {
            refresh.removeCallbacks(refreshRun)
            refresh.isRefreshing = false
            refresh.destroyDrawingCache()
            refresh.clearAnimation()
        }
    }

    fun onItemSelected(news: News) {
        if ((activity as MainActivity).isOnline()) {
            (activity as MainActivity).addFragment(NewsContentFragment.newInstance(news.id!!))
        } else {
            showSnackbar(getString(R.string.not_online))
        }
    }

    private fun showSnackbar(text: String) {
        val snack = Snackbar.make(recyclerView, text, Snackbar.LENGTH_INDEFINITE)
        snack.setWhiteText()
        snack.show()
        snack.setAction(getString(R.string.snack_bar_action)) { refreshModels() }
        newsListPresenter.cancelProgress()
    }

    fun showProgress() {
        if (refresh != null) refresh.post { refreshRun }
    }
}




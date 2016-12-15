package com.james.tinkoffnews.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.james.tinkoffnews.App
import com.james.tinkoffnews.R
import com.james.tinkoffnews.mvp.model.NewsContent
import com.james.tinkoffnews.mvp.presenter.NewsContentPresenter
import com.james.tinkoffnews.mvp.view.NewsContentView
import kotlinx.android.synthetic.main.fragment_news_content.*

class NewsContentFragment : MvpAppCompatFragment(), NewsContentView{

    @InjectPresenter
    lateinit var mNewsContentPresenter: NewsContentPresenter

    companion object {

        const val ID = "id"

        fun newInstance(id: Int): NewsContentFragment {
            val b: Bundle = Bundle()
            b.putInt(ID, id)
            val newsContentFragment = NewsContentFragment()
            newsContentFragment.arguments = b
            return newsContentFragment
        }

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        App.appComponent.inject(this)
        val v = inflater?.inflate(R.layout.fragment_news_content, container, false)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val id = arguments.getInt(ID)
        mNewsContentPresenter.loadNewsContent(id)
    }


    override fun onSuccess(newsContent: NewsContent) {
        content.text = newsContent.content
    }

    override fun onError(error: String) {
        content.text = error
    }
}
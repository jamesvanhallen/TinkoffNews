package com.james.tinkoffnews.ui.fragment

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater?.inflate(R.layout.fragment_news_content, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val id = arguments.getInt(ID)
        mNewsContentPresenter.loadNewsContent(id)
    }


    override fun onSuccess(newsContent: NewsContent) {
        val result: Spanned
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            result = Html.fromHtml(newsContent.content, Html.FROM_HTML_MODE_LEGACY)
        } else {
            result = Html.fromHtml(newsContent.content)
        }
        content.text = result
    }

    override fun onError(error: String) {
        content.text = error
    }
}
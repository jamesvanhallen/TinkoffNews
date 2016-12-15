package com.james.tinkoffnews.di

import com.james.tinkoffnews.mvp.presenter.NewsContentPresenter
import com.james.tinkoffnews.mvp.presenter.NewsListPresenter
import com.james.tinkoffnews.ui.fragment.NewsListFragment
import com.james.tinkoffnews.ui.fragment.NewsContentFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun inject(obj: NewsListFragment)
    fun inject(obj: NewsContentFragment)
    fun inject(obj: NewsListPresenter)
    fun inject(obj: NewsContentPresenter)

}
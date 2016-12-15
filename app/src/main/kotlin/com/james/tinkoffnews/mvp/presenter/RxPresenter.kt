package com.james.tinkoffnews.mvp.presenter

import android.util.Log
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import retrofit2.adapter.rxjava.HttpException
import rx.subscriptions.CompositeSubscription
import java.io.IOException

abstract class RxPresenter<V : MvpView> : MvpPresenter<V>() {

    protected var mainSubscription = CompositeSubscription()

    override fun onDestroy() {
        Log.d("REF", "onDestroyPresenter")
        mainSubscription.clear()
        super.onDestroy()
    }

    fun httpErrorHandler(throwable: Throwable): String{
        var error = "Неизвесная ошибка"
        Log.e("ERROR",  "cause " + throwable.cause.toString())
        if (throwable is HttpException){
            error = throwable.message()
        } else if (throwable is IOException){
            error = "Ошибка сервера"
        }

        return error
    }


}
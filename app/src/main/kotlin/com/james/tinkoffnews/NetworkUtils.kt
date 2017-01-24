package com.james.tinkoffnews

import android.content.Context
import retrofit2.adapter.rxjava.HttpException
import java.io.IOException


class NetworkUtils {

    companion object {

        fun httpErrorHandler(context: Context, throwable: Throwable): String {

            if (throwable is HttpException) {
                return throwable.message()
            } else if (throwable is IOException) {
                return context.getString(R.string.server_error)
            }

            throwable.message.apply {

                return throwable.message!!

            }

            return context.getString(R.string.unknown_error)
        }
    }
}
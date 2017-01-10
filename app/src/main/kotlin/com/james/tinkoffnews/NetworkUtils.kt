package com.james.tinkoffnews

import android.content.Context
import retrofit2.adapter.rxjava.HttpException
import java.io.IOException


class NetworkUtils {

    companion object {

        fun httpErrorHandler(context: Context, throwable: Throwable): String {
            var error = context.getString(R.string.unknown_error)
            if (throwable is HttpException) {
                error = throwable.message()
            } else if (throwable is IOException) {
                error = context.getString(R.string.server_error)
            }

            return error
        }
    }
}
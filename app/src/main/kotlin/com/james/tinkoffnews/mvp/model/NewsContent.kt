package com.james.tinkoffnews.mvp.model

import com.google.gson.annotations.SerializedName

open class NewsContent(@SerializedName("content") val content: String)

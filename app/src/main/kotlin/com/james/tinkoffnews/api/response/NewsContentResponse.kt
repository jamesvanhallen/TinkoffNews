package com.james.tinkoffnews.api.response

import com.google.gson.annotations.SerializedName
import com.james.tinkoffnews.mvp.model.NewsContent

class NewsContentResponse(@SerializedName("payload") val payload: NewsContent)

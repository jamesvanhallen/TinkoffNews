package com.james.tinkoffnews.api.response

import com.google.gson.annotations.SerializedName
import com.james.tinkoffnews.mvp.model.News

class NewsResponse(@SerializedName("payload") val payload: List<News>)

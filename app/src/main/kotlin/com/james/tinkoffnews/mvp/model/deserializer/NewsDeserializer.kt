package com.james.tinkoffnews.mvp.model.deserializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.james.tinkoffnews.mvp.model.News
import java.lang.reflect.Type

class NewsDeserializer : JsonDeserializer<News>{
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): News {
        val news = News()
        val jsonObj = json as JsonObject
        news.name = jsonObj.get(News.NAME).asString
        news.id = jsonObj.get(News.ID).asInt
        news.text = jsonObj.get(News.TEXT).asString
        news.baknId = jsonObj.get(News.BANK_ID).asInt
        news.publicationDate = jsonObj.get(News.DATE).asJsonObject.get(News.MILLS).asLong

        return news
    }
}
package droid.maxaria.maxander.newsapp.data

import droid.maxaria.maxander.newsapp.data.retrofit.models.city_model.CityModelItem
import droid.maxaria.maxander.newsapp.data.retrofit.models.news_models_in_list.Article
import droid.maxaria.maxander.newsapp.data.retrofit.models.news_models_in_list.SourceData
import droid.maxaria.maxander.newsapp.domain.country_model.CountryModel
import droid.maxaria.maxander.newsapp.domain.models.news_model_in_list.NewsModel
import droid.maxaria.maxander.newsapp.domain.models.news_model_in_list.Source

class Mappers {
    fun mapDataNewsListToDomainNewsList(list:List<Article>):List<NewsModel>{
        return list.map {
            NewsModel(
                author = it.author ?: NULL_PARAM,
                content = it.content?: NULL_PARAM,
                description = it.description?: NULL_PARAM,
                publishedAt = it.publishedAt?: NULL_PARAM,
                source = mapSource(it.source),
                title = it.title?: NULL_PARAM,
                url = it.url?: NULL_PARAM,
                urlToImage = it.urlToImage?: NULL_PARAM
            )
        }
    }
    private fun mapSource(source: SourceData):Source{
        val id = source.id ?: NULL_PARAM
        val name = source.name ?: NULL_PARAM
        return Source(
            id = id,
            name = name
        )
    }
    fun mapCityToCountry(cityModelItem: CityModelItem):CountryModel{
        return CountryModel(
            country = cityModelItem.country ?: NULL_PARAM,
            name = cityModelItem.name ?: NULL_PARAM,
            state = cityModelItem.state ?: NULL_PARAM
        )
    }
    companion object{
        private const val NULL_PARAM=""
    }
}
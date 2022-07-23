package droid.maxaria.maxander.newsapp.domain

import androidx.lifecycle.LiveData
import droid.maxaria.maxander.newsapp.domain.country_model.CountryModel
import droid.maxaria.maxander.newsapp.domain.models.news_model_in_list.NewsModel
import retrofit2.Response

interface Repository {
    suspend fun getNewsListByCountry(country:String): List<NewsModel>?
    suspend fun getNewsListByTag(tag:String,language:String): List<NewsModel>?
    suspend fun getCountry(lat:String,lon:String): CountryModel?
}
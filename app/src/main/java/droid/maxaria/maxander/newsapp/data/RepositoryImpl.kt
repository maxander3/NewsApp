package droid.maxaria.maxander.newsapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import droid.maxaria.maxander.newsapp.data.retrofit.ApiProvider
import droid.maxaria.maxander.newsapp.domain.Repository
import droid.maxaria.maxander.newsapp.domain.country_model.CountryModel
import droid.maxaria.maxander.newsapp.domain.models.news_model_in_list.NewsModel
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.lang.RuntimeException

class RepositoryImpl : Repository {
    //TODO DI
    private val apiProvider = ApiProvider()
    private val mapper = Mappers()
    //TODO бработка ошибок для ui
    override suspend fun getNewsList(country: String): List<NewsModel>? {
        try {
            val response = apiProvider.getNewsListApi().listNews(country)
            return if (response.isSuccessful) {
                if (response.body()?.status == RETROFIT_SUCCESS_RESULT) {
                    if (response.body()!=null) {
                        mapper.mapDataNewsListToDomainNewsList(response.body()!!.articles)
                    }else{
                        return null
                    }
                } else {
                    throw RuntimeException("Server Error")
                }
            } else {
                null
            }
        } catch (e: HttpException) {
            throw RuntimeException("Something went wrong")
        } catch (e: IOException) {
            throw RuntimeException("Please check your network connection")
        } catch (e: Exception) {
            throw RuntimeException("Something went wrong")
        }
    }

    override suspend fun getCountry(lat: String, lon: String): CountryModel? {
        try {
            val response = apiProvider.getCountryApi().getCity(lat, lon)
            return if (response.isSuccessful) {
                if (response.body() != null) {
                    mapper.mapCityToCountry(response.body()!!.first())
                } else null
            } else {
                null
            }
        } catch (e: HttpException) {
        throw RuntimeException("Something went wrong")
    } catch (e: IOException) {
        throw RuntimeException("Please check your network connection")
    } catch (e: Exception) {
        throw RuntimeException("Something went wrong")
    }
    }

    companion object {
        const val RETROFIT_SUCCESS_RESULT = "ok"
    }
}
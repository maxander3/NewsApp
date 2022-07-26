package droid.maxaria.maxander.newsapp.data

import droid.maxaria.maxander.newsapp.data.retrofit.ApiProvider
import droid.maxaria.maxander.newsapp.domain.NewsRepository
import droid.maxaria.maxander.newsapp.domain.country_model.CountryModel
import droid.maxaria.maxander.newsapp.domain.models.news_model_in_list.NewsModel
import retrofit2.HttpException
import java.io.IOException
import java.lang.RuntimeException

class NewsRepositoryImpl(private val apiProvider: ApiProvider) : NewsRepository {
    //TODO DI
    private val mapper = Mappers()

    override suspend fun getNewsListByCountry(country: String): List<NewsModel>? {
        try {
            val response = apiProvider.getNewsListByCountryApi().listNewsCountry(country = country)
            return if (response.isSuccessful) {
                if (response.body()?.status == RETROFIT_SUCCESS_RESULT) {
                    response.body()?.let {
                        mapper.mapDataNewsListToDomainNewsList(it. articles)
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

    override suspend fun getNewsListByTag(tag: String, language: String): List<NewsModel>? {
        try {
            val response = apiProvider.getNewsListByTagApi()
                .newsListEverything(q = tag,language = language)
            return if (response.isSuccessful) {
                if (response.body()?.status == RETROFIT_SUCCESS_RESULT) {
                    response.body()?.let {
                        mapper.mapDataNewsListToDomainNewsList(it. articles)
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
                response.body()?.let { mapper.mapCityToCountry(it.first()) }
            }else{
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
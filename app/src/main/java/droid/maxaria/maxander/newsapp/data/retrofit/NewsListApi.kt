package droid.maxaria.maxander.newsapp.data.retrofit

import droid.maxaria.maxander.newsapp.data.retrofit.models.news_models_in_list.NewsModelInList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsListApi {
    @GET("v2/top-headlines")
    suspend fun listNews(
        @Query("country")country:String = "ru",
        @Query("apiKey")apiKey:String = "4f09a82b08d14167aebe3e740105c3a9",
        @Query("pageSize")pageSize:String = "20",
        @Query("page")page:String = "1"
    ):Response<NewsModelInList>
}
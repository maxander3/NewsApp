package droid.maxaria.maxander.newsapp.data.retrofit

import droid.maxaria.maxander.newsapp.data.retrofit.models.news_models_in_list.NewsModelInList
import org.intellij.lang.annotations.Language
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsListApiEverything {
    @GET("v2/everything")
    suspend fun newsListEverything(
        @Query("q")q:String,
        @Query("apiKey")apiKey:String = "4f09a82b08d14167aebe3e740105c3a9",
        @Query("pageSize")pageSize:String = "50",
        @Query("language")language: String,
        @Query("sortBy")sortBy:String = "popularity"
    ):Response<NewsModelInList>
}
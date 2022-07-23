package droid.maxaria.maxander.newsapp.data.retrofit


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ApiProvider {

    private val news by lazy { initNewsApi() }
    private val country by lazy {initGeoCodeApi()}

    private fun initNewsApi() = Retrofit.Builder()
        .baseUrl("https://newsapi.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private fun initGeoCodeApi() = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getNewsListByCountryApi():NewsListApiCountry = news.create(NewsListApiCountry::class.java)
    fun getCountryApi():CityApi = country.create(CityApi::class.java)
    fun getNewsListByTagApi():NewsListApiEverything = news.create(NewsListApiEverything::class.java)
}
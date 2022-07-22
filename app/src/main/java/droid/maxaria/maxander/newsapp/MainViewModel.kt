package droid.maxaria.maxander.newsapp

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import droid.maxaria.maxander.newsapp.data.RepositoryImpl
import droid.maxaria.maxander.newsapp.domain.Repository
import droid.maxaria.maxander.newsapp.domain.country_model.CountryModel
import droid.maxaria.maxander.newsapp.domain.models.news_model_in_list.NewsModel
import droid.maxaria.maxander.newsapp.domain.usecases.GetCountryUseCase
import droid.maxaria.maxander.newsapp.domain.usecases.GetNewsListUseCase
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {
    //TODO DI
    private val repository = RepositoryImpl()
    private val getNewsListUseCase = GetNewsListUseCase(repository)
    private val getCountryUseCase = GetCountryUseCase(repository)

    private val _newsList = MutableLiveData<List<NewsModel>>()
    val newsList:LiveData<List<NewsModel>>
        get() = _newsList
    private val _country = MutableLiveData<CountryModel>()
    val country:LiveData<CountryModel>
        get() = _country

    @SuppressLint("NullSafeMutableLiveData")
    fun getNewsList(country:String){
        viewModelScope.launch {
            val response = getNewsListUseCase(country)
            if (response != null) {
                _newsList.postValue(response)
            }else{
                //TODO обработка ошибки
            }
        }
    }

    @SuppressLint("NullSafeMutableLiveData")
    fun getCountryByCord(lat: Double, lon: Double){
        viewModelScope.launch {
            val response = getCountryUseCase(lat.toString(), lon.toString())
            if (response != null){
                _country.postValue(response)
            }else{
                //TODO обработка ошибки
            }
        }
    }

}
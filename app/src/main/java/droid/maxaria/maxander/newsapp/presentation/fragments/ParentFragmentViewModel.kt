package droid.maxaria.maxander.newsapp.presentation.fragments

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import droid.maxaria.maxander.newsapp.data.RepositoryImpl
import droid.maxaria.maxander.newsapp.data.retrofit.ApiProvider
import droid.maxaria.maxander.newsapp.domain.country_model.CountryModel
import droid.maxaria.maxander.newsapp.domain.models.news_model_in_list.NewsModel
import droid.maxaria.maxander.newsapp.domain.usecases.GetNewsListUseCase
import kotlinx.coroutines.launch

class ParentFragmentViewModel : ViewModel() {
    //TODO DI
    private val apiProvider = ApiProvider()
    private val repository = RepositoryImpl(apiProvider)
    private val getNewsListUseCase = GetNewsListUseCase(repository)

    private val _newsList = MutableLiveData<List<NewsModel>>()
    val newsList: LiveData<List<NewsModel>>
        get() = _newsList
    val currentNewsTag = MutableLiveData<String>()
    private var page:Int = 1


    fun getNewsList(tag:String){
        page = 1
        viewModelScope.launch {
            val response = getNewsListUseCase(tag,page.toString())
            Log.d("TAG",response.toString())
            if (response != null) {
                _newsList.postValue(response)
            }else{
                //TODO обработка ошибки
            }
        }
    }


}
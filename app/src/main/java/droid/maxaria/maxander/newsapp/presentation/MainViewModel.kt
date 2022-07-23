package droid.maxaria.maxander.newsapp.presentation

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import droid.maxaria.maxander.newsapp.data.RepositoryImpl
import droid.maxaria.maxander.newsapp.data.retrofit.ApiProvider
import droid.maxaria.maxander.newsapp.domain.country_model.CountryModel
import droid.maxaria.maxander.newsapp.domain.usecases.GetCountryUseCase
import kotlinx.coroutines.launch

class MainViewModel:ViewModel() {
    //TODO DI
    private val apiProvider = ApiProvider()
    private val repository = RepositoryImpl(apiProvider)
    private val getCountryUseCase = GetCountryUseCase(repository)

    private val _error = MutableLiveData<Unit>()
    val error: LiveData<Unit>
        get() = _error

    private val _country = MutableLiveData<CountryModel>()
    val country:LiveData<CountryModel>
        get() = _country



    @SuppressLint("NullSafeMutableLiveData")
    fun getCountryByCord(lat: Double, lon: Double){
        viewModelScope.launch {
            val response = getCountryUseCase(lat.toString(), lon.toString())
            if (response != null){
                _country.postValue(response)
            }else{
                _error.value = Unit
            }
        }
    }

}
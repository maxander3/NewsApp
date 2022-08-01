package droid.maxaria.maxander.newsapp.presentation

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import droid.maxaria.maxander.newsapp.data.NewsRepositoryImpl
import droid.maxaria.maxander.newsapp.data.retrofit.ApiProvider
import droid.maxaria.maxander.newsapp.domain.country_model.CountryModel
import droid.maxaria.maxander.newsapp.domain.usecases.GetCountryUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCountryUseCase: GetCountryUseCase
):ViewModel() {

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
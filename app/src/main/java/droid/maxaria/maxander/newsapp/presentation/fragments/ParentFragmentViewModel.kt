package droid.maxaria.maxander.newsapp.presentation.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import droid.maxaria.maxander.newsapp.data.NewsRepositoryImpl
import droid.maxaria.maxander.newsapp.data.retrofit.ApiProvider
import droid.maxaria.maxander.newsapp.domain.models.news_model_in_list.NewsModel
import droid.maxaria.maxander.newsapp.domain.usecases.GetNewsListByCountryUseCase
import droid.maxaria.maxander.newsapp.domain.usecases.GetNewsListByTagUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ParentFragmentViewModel @Inject constructor(
    private val getNewsListByCountryUseCase: GetNewsListByCountryUseCase,
    private val getNewsListByTagUseCase: GetNewsListByTagUseCase
) : ViewModel() {
    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _newsList = MutableLiveData<List<NewsModel>>()
    val newsList: LiveData<List<NewsModel>>
        get() = _newsList

    val currentCountry = MutableLiveData<String>()
    val currentNewsTag = MutableLiveData<String>()

    fun getNewsListByCountry(tag: String) {
        viewModelScope.launch {
            val response = getNewsListByCountryUseCase(validateCountry(tag))
            if (response != null) {
                if(response.isNotEmpty()) {
                    _newsList.postValue(response)
                }else _error.value = NOT_FOUND
            } else {
                _error.value = INTERNET_ERROR
            }
        }
    }

    fun getNewsListByTag(tag: String, language: String) {
        if (tag != EMPTY_REQUEST) {
            viewModelScope.launch {
                val response = getNewsListByTagUseCase(tag, validateLanguage(language))
                if (response != null) {
                    if(response.isNotEmpty()) {
                        _newsList.postValue(response)
                    }else _error.value = NOT_FOUND
                } else {
                    _error.value = INTERNET_ERROR
                }
            }
        } else
            _error.postValue(INCORRECT_REQUEST)
    }

    private fun validateCountry(_country:String): String {
        val country = _country.lowercase()
        for (element in AVAILABLE_COUNTRIES)
            if (country == element) return country
        return AVAILABLE_COUNTRIES[0]
    }

    private fun validateLanguage(_language: String): String {
        val language = _language.lowercase()
        for (element in AVAILABLE_LANGUAGES)
            if (language == element) return language
        return AVAILABLE_LANGUAGES[0]
    }

    companion object {
        const val EMPTY_REQUEST = ""
        const val NOT_FOUND = "Not found"
        const val INCORRECT_REQUEST = "Incorrect request"
        const val INTERNET_ERROR = "Please check ur internet connection"
        val AVAILABLE_LANGUAGES = listOf(
            "en","ar", "de",  "es", "fr", "he", "it", "nl", "no", "pt", "ru", "sv", "ud", "zh"
        )
        val AVAILABLE_COUNTRIES = listOf(
            "us","ae","ar","at","au","be","bg","br","ca","ch","cn","co","cu","cz","de","eg","fr","gb",
            "gr","hk","hu","id","ie","il","in","it","jp","kr","lt","lv","ma","mx","my","ng","nl",
            "no","nz","ph","pl","pt","ro","rs","ru","sa","se","sg","si","sk","th","tr","tw","ua",
            "ve","za"
        )
    }
}
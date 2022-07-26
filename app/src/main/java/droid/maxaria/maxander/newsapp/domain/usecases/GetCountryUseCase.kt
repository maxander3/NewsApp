package droid.maxaria.maxander.newsapp.domain.usecases

import droid.maxaria.maxander.newsapp.domain.NewsRepository
import droid.maxaria.maxander.newsapp.domain.country_model.CountryModel

class GetCountryUseCase(private val repository: NewsRepository) {
    suspend operator fun invoke(lat:String,lon:String):CountryModel?{
        return repository.getCountry(lat,lon)
    }
}
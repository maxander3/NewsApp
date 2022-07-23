package droid.maxaria.maxander.newsapp.domain.usecases

import droid.maxaria.maxander.newsapp.domain.Repository
import droid.maxaria.maxander.newsapp.domain.models.news_model_in_list.NewsModel

class GetNewsListByCountryUseCase(private val repository: Repository) {
    suspend operator fun invoke(country:String): List<NewsModel>? {
        return repository.getNewsListByCountry(country)
    }
}
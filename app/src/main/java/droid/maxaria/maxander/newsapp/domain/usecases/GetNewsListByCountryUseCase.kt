package droid.maxaria.maxander.newsapp.domain.usecases

import droid.maxaria.maxander.newsapp.domain.NewsRepository
import droid.maxaria.maxander.newsapp.domain.models.news_model_in_list.NewsModel
import javax.inject.Inject

class GetNewsListByCountryUseCase @Inject constructor(private val repository: NewsRepository) {
    suspend operator fun invoke(country:String): List<NewsModel>? {
        return repository.getNewsListByCountry(country)
    }
}
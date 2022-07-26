package droid.maxaria.maxander.newsapp.domain.usecases

import droid.maxaria.maxander.newsapp.domain.NewsRepository
import droid.maxaria.maxander.newsapp.domain.models.news_model_in_list.NewsModel

class GetNewsListByTagUseCase(private val repository: NewsRepository) {
    suspend operator fun invoke(tag:String,language:String): List<NewsModel>?{
        return repository.getNewsListByTag(tag,language)
    }
}
package droid.maxaria.maxander.newsapp.domain.usecases

import androidx.lifecycle.LiveData
import droid.maxaria.maxander.newsapp.domain.Repository
import droid.maxaria.maxander.newsapp.domain.models.news_model_in_list.NewsModel
import retrofit2.Response

class GetNewsListUseCase(private val repository: Repository) {
    suspend operator fun invoke(country:String,page:String): List<NewsModel>? {
        return repository.getNewsList(country,page)
    }
}